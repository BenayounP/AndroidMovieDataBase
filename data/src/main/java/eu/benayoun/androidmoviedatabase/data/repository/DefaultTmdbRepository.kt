package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbUpdateStatus
import eu.benayoun.androidmoviedatabase.data.source.local.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.network.TmdbDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


internal class DefaultTmdbRepository(private val tmdbDataSource: TmdbDataSource,
                                     private val tmdbCache: TmdbCache,
                                     private val externalScope: CoroutineScope,
                                     private val dispatcher: CoroutineDispatcher
) : TmdbRepository {

    private val popularMoviesMutex = Mutex()

    private val _updateFlow = MutableStateFlow<TmdbUpdateStatus>(TmdbUpdateStatus.Off)

    override suspend fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> = tmdbCache.getTmdbMetaDataFlow()

    override suspend fun getPopularMovieListFlow(): Flow<List<TmdbMovie>> {
        return tmdbCache.getTmdbMovieListFlow()
    }

    override suspend fun getTmdbUpdateStatusFlow(): Flow<TmdbUpdateStatus> = _updateFlow

    override fun updateTmdbMovies() {
        externalScope.launch(dispatcher) {
            popularMoviesMutex.withLock() {
                // we are updating and we say it!
                _updateFlow.value = TmdbUpdateStatus.Updating

                var lastInternetSuccessTimeStamp: Long = TmdbMetadata.INVALID_TIMESTAMP
                val tmdbSourceStatus: TmdbSourceStatus
                val tmdbPopularMovieList: List<TmdbMovie>
                // Step 1: try to get data on TMDB Server
                val tmdbAPIResponse = tmdbDataSource.getPopularMovies()

                // Step 2 Success : save films in db and update metadata
                if (tmdbAPIResponse is TmdbAPIResponse.Success) {
                    tmdbSourceStatus = TmdbSourceStatus.Internet
                    lastInternetSuccessTimeStamp = System.currentTimeMillis()
                    tmdbPopularMovieList = tmdbAPIResponse.tmdbMovieList
                    tmdbCache.saveTmdbMovieList(tmdbPopularMovieList)
                    saveMetaData(tmdbSourceStatus, lastInternetSuccessTimeStamp)
                }
                //Step 2 failure : No data from Internet, just update metadate
                else {
                    val tmdbAPIError = (tmdbAPIResponse as TmdbAPIResponse.Error).tmdbAPIError
                    tmdbSourceStatus = TmdbSourceStatus.Cache(tmdbAPIError)
                    saveMetaData(tmdbSourceStatus, lastInternetSuccessTimeStamp)
                }
                // we stop updating and we say it!
                _updateFlow.value=TmdbUpdateStatus.Off
            }
        }
    }

    private suspend fun saveMetaData(tmdbSourceStatus: TmdbSourceStatus,lastInternetSuccessTimeStamp: Long){
        tmdbCache.saveTmdbMetaData(
            TmdbMetadata(
                tmdbSourceStatus,
                lastInternetSuccessTimeStamp
            )
        )
    }
}