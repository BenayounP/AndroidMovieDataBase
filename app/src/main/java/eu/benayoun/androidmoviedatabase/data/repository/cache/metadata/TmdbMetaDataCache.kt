package eu.benayoun.androidmoviedatabase.data.repository.cache.metadata

import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin
import kotlinx.coroutines.flow.Flow

interface TmdbMetaDataCache {
    fun getTmdbOriginFlow(): Flow<TmdbOrigin>
    suspend fun saveTmdbOrigin(tmdbOrigin: TmdbOrigin)
}