package eu.benayoun.androidmoviedatabase.data.source.local.metadata

import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeTmdbMetaDataCache : TmdbMetaDataCache {
    private val _tmdbMetaDataFlow = MutableStateFlow<TmdbMetadata>(TmdbMetadata())

    override suspend fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> = _tmdbMetaDataFlow

    override suspend fun saveTmdbMetaData(tmdbMetadata: TmdbMetadata) {
        _tmdbMetaDataFlow.value=tmdbMetadata
    }


}