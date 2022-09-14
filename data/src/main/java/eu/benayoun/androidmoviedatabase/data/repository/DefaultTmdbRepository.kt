package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.repository.cache.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.TmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


internal class DefaultTmdbRepository(private val tmdbDataSource: TmdbDataSource,
                            private val tmdbCache: TmdbCache
) : TmdbRepository {
    override suspend fun getPopularMovieListFlow(): Flow<List<TmdbMovie>> {
        return flow{
            var lastInternetSuccessTimeStamp : Long=-1
            var tmdbSourceStatus : eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
            var tmdbPopularMovieList : List<TmdbMovie> = listOf()
            // Step 1: try to get data on TMDB Server
            LogUtils.v("Step 1: try to get data on TMDB Server")
            var tmdbAPIResponse = tmdbDataSource.getPopularMovies()

            // Step 2: There is data: save data in cache
            if (tmdbAPIResponse is eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse.Success){
                LogUtils.v("Step 2: There is data: save data in cache")
                tmdbSourceStatus = eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus.Internet()
                lastInternetSuccessTimeStamp = System.currentTimeMillis()
                tmdbPopularMovieList = tmdbAPIResponse.tmdbMovieList
                tmdbCache.saveTmdbMovieList(tmdbPopularMovieList)
            }
            //Step 3: No data from Internet, try to get it from cache
            else
            {
                LogUtils.v("Step 3: No data from Internet, try to get it from cache")
                val tmdbAPIError = (tmdbAPIResponse as eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse.Error).tmdbAPIError
                tmdbSourceStatus = eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus.Cache(tmdbAPIError)
                tmdbPopularMovieList =tmdbCache.getTmdbMovieList()
            }

            // Step 4: emit
            LogUtils.v("Step 4: Emit")
            tmdbCache.saveTmdbMetaData(
                eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata(
                    tmdbSourceStatus,
                    lastInternetSuccessTimeStamp
                )
            )
            emit(tmdbPopularMovieList)
        }
    }

    override suspend fun getTmdbMetaDataFlow(): Flow<eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata> = tmdbCache.getTmdbMetaDataFlow()


}