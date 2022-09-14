package eu.benayoun.androidmoviedatabase.data.repository.cache.metadata

import kotlinx.coroutines.flow.Flow

internal interface TmdbMetaDataCache {
    fun getTmdbMetaDataFlow(): Flow<eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata>
    suspend fun saveTmdbMetaData(tmdbMetadata : eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata)
}