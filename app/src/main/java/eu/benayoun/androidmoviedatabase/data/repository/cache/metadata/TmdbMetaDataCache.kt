package eu.benayoun.androidmoviedatabase.data.repository.cache.metadata

import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin
import kotlinx.coroutines.flow.Flow

interface TmdbMetaDataCache {
    fun getTmdbMetaDataFlow(): Flow<TmdbMetadata>
    suspend fun saveTmdbMetaData(tmdbMetadata : TmdbMetadata)
}