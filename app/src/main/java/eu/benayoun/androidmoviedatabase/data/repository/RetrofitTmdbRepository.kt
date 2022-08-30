package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.repository.cache.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.source.TmdbAPIResponse
import eu.benayoun.androidmoviedatabase.data.source.TmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RetrofitTmdbRepository(private val tmdbDataSource: TmdbDataSource,
                             private val tmdbcache: TmdbCache
) : TmdbRepository {
    override suspend fun getPopularMovieListFlow(): Flow<List<TmdbMovie>> {
        return flow{

            // Step 1: try to get data on TMDB Server
            var tmdbPopularMovieList : List<TmdbMovie> = listOf()
            var tmdbAPIResponse = tmdbDataSource.getPopularMovies()

            // Step 2: There is data: save data in cache
            if (tmdbAPIResponse is TmdbAPIResponse.Success){
                tmdbPopularMovieList = tmdbAPIResponse.tmdbMovieList
                tmdbcache.saveTmdbMovieList(tmdbPopularMovieList)
            }
            //Step 2: no data, try to get it from cache
            else
            {
                val tmdbAPIError = (tmdbAPIResponse as TmdbAPIResponse.Error).tmdbAPIError
                val logMessage : String = when(tmdbAPIError){
                    is TmdbAPIError.NoInternet -> "NoInternet"
                    is TmdbAPIError.ToolError -> "ToolError"
                    is TmdbAPIError.NoData -> "NoData"
                    is TmdbAPIError.Exception -> "Exception: ${tmdbAPIError.localizedMessage}"
                }
                LogUtils.v(logMessage)
                tmdbPopularMovieList =tmdbcache.getTmdbMovieList()
            }

            // Step3: emit
            emit(tmdbPopularMovieList)
        }
    }
}