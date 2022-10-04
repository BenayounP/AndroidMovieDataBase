package eu.benayoun.androidmoviedatabase.data.source.local.metadata

import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import kotlinx.coroutines.flow.Flow

internal interface TmdbMetaDataCache {
    suspend fun getTmdbMetaDataFlow(): Flow<TmdbMetadata>
    suspend fun saveTmdbMetaData(tmdbMetadata : TmdbMetadata)
}