package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.repository.cache.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.source.TmdbAPIResponse
import eu.benayoun.androidmoviedatabase.data.source.TmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultTmdbRepository(private val tmdbDataSource: TmdbDataSource,
                            private val tmdbcache: TmdbCache
) : TmdbRepository {
    override suspend fun getPopularMovieListFlow(): Flow<TmdbMetaMovieList> {
        return flow{

            var dataOrigin : DataOrigin

            // Step 1: try to get data on TMDB Server
            LogUtils.v("Step 1: try to get data on TMDB Server")
            var tmdbPopularMovieList : List<TmdbMovie> = listOf()
            var tmdbAPIResponse = tmdbDataSource.getPopularMovies()

            // Step 2: There is data: save data in cache
            if (tmdbAPIResponse is TmdbAPIResponse.Success){
                LogUtils.v("Step 2: There is data: save data in cache")
                dataOrigin = DataOrigin.Internet()
                tmdbPopularMovieList = tmdbAPIResponse.tmdbMovieList
                tmdbcache.saveTmdbMovieList(tmdbPopularMovieList)
            }
            //Step 3: No data from Internet, try to get it from cache
            else
            {
                LogUtils.v("Step 3: No data from Internet, try to get it from cache")
                val tmdbAPIError = (tmdbAPIResponse as TmdbAPIResponse.Error).tmdbAPIError
                dataOrigin = DataOrigin.Cache(tmdbAPIError)
                tmdbPopularMovieList =tmdbcache.getTmdbMovieList()
            }

            // Step 4: emit
            LogUtils.v("Step 4: Emit")
            emit(TmdbMetaMovieList(tmdbPopularMovieList,dataOrigin))
        }
    }
}