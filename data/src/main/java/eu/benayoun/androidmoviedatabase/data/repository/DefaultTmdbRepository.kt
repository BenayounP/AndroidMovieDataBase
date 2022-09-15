package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.data.source.local.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.network.TmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


internal class DefaultTmdbRepository(private val tmdbDataSource: TmdbDataSource,
                                     private val tmdbCache: TmdbCache
) : TmdbRepository {
    override suspend fun updateTmdbMovies()  {
            var lastInternetSuccessTimeStamp : Long=-1
            var tmdbSourceStatus : TmdbSourceStatus
            var tmdbPopularMovieList : List<TmdbMovie> = listOf()
            // Step 1: try to get data on TMDB Server
            LogUtils.v("Step 1: try to get data on TMDB Server")
            var tmdbAPIResponse = tmdbDataSource.getPopularMovies()

            // Step 2: There is data: save data in cache
            if (tmdbAPIResponse is TmdbAPIResponse.Success){
                LogUtils.v("Step 2: There is data: save data in cache")
                tmdbSourceStatus = TmdbSourceStatus.Internet()
                lastInternetSuccessTimeStamp = System.currentTimeMillis()
                tmdbPopularMovieList = tmdbAPIResponse.tmdbMovieList
                tmdbCache.saveTmdbMovieList(tmdbPopularMovieList)
            }
            //Step 3: No data from Internet, update metadate
            else
            {
                LogUtils.v("Step 3: No data from Internet, try to get it from cache")
                val tmdbAPIError = (tmdbAPIResponse as TmdbAPIResponse.Error).tmdbAPIError
                tmdbSourceStatus = TmdbSourceStatus.Cache(tmdbAPIError)
            }

            // Step 4: save Tmdb data
            LogUtils.v("Step 4: Emit")
            tmdbCache.saveTmdbMetaData(
                TmdbMetadata(
                    tmdbSourceStatus,
                    lastInternetSuccessTimeStamp
                )
            )
    }

    override suspend fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> = tmdbCache.getTmdbMetaDataFlow()

    override suspend fun getPopularMovieListFlow(): Flow<List<TmdbMovie>> =
        tmdbCache.getTmdbMovieList()

}