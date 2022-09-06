package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin
import eu.benayoun.androidmoviedatabase.data.repository.cache.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.TmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultTmdbRepository(private val tmdbDataSource: TmdbDataSource,
                            private val tmdbCache: TmdbCache
) : TmdbRepository {
    override suspend fun getPopularMovieListFlow(): Flow<List<TmdbMovie>> {
        return flow{
            var tmdbOrigin : TmdbOrigin
            var tmdbPopularMovieList : List<TmdbMovie> = listOf()
            // Step 1: try to get data on TMDB Server
            LogUtils.v("Step 1: try to get data on TMDB Server")
            var tmdbAPIResponse = tmdbDataSource.getPopularMovies()

            // Step 2: There is data: save data in cache
            if (tmdbAPIResponse is TmdbAPIResponse.Success){
                LogUtils.v("Step 2: There is data: save data in cache")
                tmdbOrigin = TmdbOrigin.Internet()
                tmdbPopularMovieList = tmdbAPIResponse.tmdbMovieList
                tmdbCache.saveTmdbMovieList(tmdbPopularMovieList)
            }
            //Step 3: No data from Internet, try to get it from cache
            else
            {
                LogUtils.v("Step 3: No data from Internet, try to get it from cache")
                val tmdbAPIError = (tmdbAPIResponse as TmdbAPIResponse.Error).tmdbAPIError
                tmdbOrigin = TmdbOrigin.Cache(tmdbAPIError)
                tmdbPopularMovieList =tmdbCache.getTmdbMovieList()
            }

            // Step 4: emit
            LogUtils.v("Step 4: Emit")
            tmdbCache.saveTmdbOrigin(tmdbOrigin)
            emit(tmdbPopularMovieList)
        }
    }

    override suspend fun getTmdbOriginFlow(): Flow<TmdbOrigin>  = tmdbCache.loadTmdbOrigin()
}