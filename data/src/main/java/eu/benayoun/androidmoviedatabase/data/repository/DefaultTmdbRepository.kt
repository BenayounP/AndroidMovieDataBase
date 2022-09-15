package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.data.source.local.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.network.TmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


internal class DefaultTmdbRepository(private val tmdbDataSource: TmdbDataSource,
                                     private val tmdbCache: TmdbCache,
                                     private val externalScope: CoroutineScope
) : TmdbRepository {
    override suspend fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> = tmdbCache.getTmdbMetaDataFlow()

    override suspend fun getPopularMovieListFlow(): Flow<List<TmdbMovie>> =
        tmdbCache.getTmdbMovieList()

    override fun updateTmdbMovies() {
        externalScope.launch(Dispatchers.IO) {
            var lastInternetSuccessTimeStamp: Long = -1
            var tmdbSourceStatus: TmdbSourceStatus
            var tmdbPopularMovieList: List<TmdbMovie>
            // Step 1: try to get data on TMDB Server
            LogUtils.v("Step 1: try to get data on TMDB Server")
            var tmdbAPIResponse = tmdbDataSource.getPopularMovies()

            // Step 2 Success : save films in db and update metadat
            if (tmdbAPIResponse is TmdbAPIResponse.Success) {
                LogUtils.v("Step 2 SUCCESS: save data")
                tmdbSourceStatus = TmdbSourceStatus.Internet()
                lastInternetSuccessTimeStamp = System.currentTimeMillis()
                tmdbPopularMovieList = tmdbAPIResponse.tmdbMovieList
                tmdbCache.saveTmdbMovieList(tmdbPopularMovieList)
                saveMetaData(tmdbSourceStatus,lastInternetSuccessTimeStamp)
            }
            //Step 2 failure : No data from Internet, just update metadate
            else {
                LogUtils.v("Step 2: FAILURE")
                val tmdbAPIError = (tmdbAPIResponse as TmdbAPIResponse.Error).tmdbAPIError
                tmdbSourceStatus = TmdbSourceStatus.Cache(tmdbAPIError)
                saveMetaData(tmdbSourceStatus,lastInternetSuccessTimeStamp)
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