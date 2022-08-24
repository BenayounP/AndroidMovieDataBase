package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.source.TmdbDataSource
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RetrofitTmdbRepository(private val tmdbDataSource: TmdbDataSource
) : TmdbRepository {
    override fun getPopularMoviesFlow(): Flow<List<TmdbMovie>> {
        return flow{
        val popularMovies = tmdbDataSource.getPopularMovies()
            if (popularMovies.size!=0){
                emit(popularMovies)
            }
        }
    }
}