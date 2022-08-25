package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.repository.cache.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.TmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RetrofitTmdbRepository(private val tmdbDataSource: TmdbDataSource,
                             private val tmdbcache: TmdbCache
) : TmdbRepository {
    override suspend fun getPopularMoviesFlow(): Flow<List<TmdbMovie>> {
        return flow{

            // Step 1: try to get data on TMDB Server 🤞
            LogUtils.e("---------- step1")
            var popularMovies = tmdbDataSource.getPopularMovies()

            // Step 2: There is data: save data in cache
            if (popularMovies.size!=0){
                LogUtils.e("---------- step2: data")
                tmdbcache.saveTmdbMovies(popularMovies)
            }
            //Step 2: no data, try to get it from cache
            else
            {
                LogUtils.e("---------- step2: NO data")
                popularMovies =tmdbcache.getTmdbMovies()
            }

            // Step3: emit
            LogUtils.e("---------- step3")
            emit(popularMovies)
            LogUtils.e("----------  end")
        }
    }
}