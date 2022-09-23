package eu.benayoun.androidmoviedatabase.data.source.local

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTmdbCache : TmdbCache {
    override suspend fun getTmdbMovieList(): Flow<List<TmdbMovie>> = flow{ emit(listOf())}

    override suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>) {
        TODO("Not yet implemented")
    }

    override fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTmdbMetaData(tmdbMetadata: TmdbMetadata) {
        TODO("Not yet implemented")
    }
}