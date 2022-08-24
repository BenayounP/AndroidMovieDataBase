package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.source.TMDBDataSource
import eu.pbenayoun.thatdmdbapp.repository.model.TMDBMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

class RetrofitTMDBRepository(private val TMDBDataSource: TMDBDataSource,
                             private val externalScope : CoroutineScope,
) : TMDBRepository {
    override suspend fun getPopularMoviesFlow(): Flow<List<TMDBMovie>> {
        return flow{
        val popularMovies = TMDBDataSource.getPopularMovies()
            if (popularMovies.size!=0){
                emit(popularMovies)
            }
        }.shareIn(externalScope,started = SharingStarted.WhileSubscribed())
    }
}