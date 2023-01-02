package eu.benayoun.androidmoviedatabase.data.source.local.metadata

import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import kotlinx.coroutines.flow.Flow

internal interface TmdbMetaDataCache {
    // get the flow with metadata
    suspend fun getTmdbMetaDataFlow(): Flow<TmdbMetadata>

    // save metadata on device "hard drive"
    suspend fun saveTmdbMetaData(tmdbMetadata : TmdbMetadata)
}