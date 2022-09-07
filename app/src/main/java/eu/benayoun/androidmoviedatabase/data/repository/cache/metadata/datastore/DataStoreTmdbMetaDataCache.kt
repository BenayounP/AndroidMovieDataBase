package eu.benayoun.androidmoviedatabase.data.repository.cache.metadata.datastore

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import eu.benayoun.androidmoviedatabase.TmdbMetadataSerialized
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin
import eu.benayoun.androidmoviedatabase.data.repository.cache.metadata.TmdbMetaDataCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// this class wouldn't have exist without this response on StackOverflow
// https://stackoverflow.com/a/66101769/1859993

private val Context.tmdbOriginDataStore by dataStore(
    fileName = "tmdb_meta",
    serializer = TmdbMetadataSerializer,
    corruptionHandler = ReplaceFileCorruptionHandler(
        produceNewData = { TmdbMetadataSerialized.getDefaultInstance()}
    )
)

class DataStoreTmdbMetaDataCache(appContext: Context) :
    TmdbMetaDataCache {
    private  val tmdbOriginDataStore = appContext.tmdbOriginDataStore

    override fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> = tmdbOriginDataStore.data.map{ tmdbMetadataSerialized: TmdbMetadataSerialized ->
        TmdbMetadata(mapToOrigin(tmdbMetadataSerialized),tmdbMetadataSerialized.lastInternetSuccessTimeStamp)
    }

    override suspend fun saveTmdbMetaData(tmdbMetadata: TmdbMetadata){
        tmdbOriginDataStore.updateData{tmdbMetadataSerialized: TmdbMetadataSerialized ->
           val builder = tmdbMetadataSerialized.toBuilder()
            val tmdbOrigin = tmdbMetadata.tmdbOrigin
            when(tmdbOrigin){
                is TmdbOrigin.Internet -> builder.lastInternetSuccessTimeStamp = System.currentTimeMillis()
                is TmdbOrigin.Cache -> processTmdbAPIError(tmdbOrigin.tmdbAPIError,builder)
                is TmdbOrigin.Unknown -> null // nothing to do here
            }
            builder.tmdbOriginEnum = extractTmdbSourceEnum(tmdbOrigin)
            builder.build()
        }
    }

    // internal cooking

    private fun extractTmdbSourceEnum(tmdbOrigin : TmdbOrigin) : TmdbMetadataSerialized.TmdbOriginEnum{
        return when (tmdbOrigin){
            is TmdbOrigin.Internet -> TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_INTERNET
            is TmdbOrigin.Cache -> TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_CACHE
            is TmdbOrigin.Unknown -> TmdbMetadataSerialized.TmdbOriginEnum.UNRECOGNIZED
        }

    }

    private fun mapToOrigin(tmdbMetadataSerialized: TmdbMetadataSerialized) : TmdbOrigin {
        return when(tmdbMetadataSerialized.tmdbOriginEnum){
            TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_INTERNET -> TmdbOrigin.Internet()
            TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_CACHE -> {
                TmdbOrigin.Cache(
                    extractTmdbAPIError(tmdbMetadataSerialized)
                )
            }
            TmdbMetadataSerialized.TmdbOriginEnum.UNRECOGNIZED -> TmdbOrigin.Unknown()
        }
    }

    private fun processTmdbAPIError(tmdbAPIError: TmdbAPIError, builder :  TmdbMetadataSerialized.Builder) {
        when(tmdbAPIError){
            is TmdbAPIError.ToolError -> builder.tmdbAPIError = TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_TOOL_ERROR
            is TmdbAPIError.Exception -> {
                builder.tmdbAPIError = TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_EXCEPTION
                builder.exceptionLocalizedMessage = tmdbAPIError.localizedMessage
            }
            is TmdbAPIError.NoData -> TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_DATA
            is TmdbAPIError.NoInternet -> TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_INTERNET
            is TmdbAPIError.Unknown -> TmdbMetadataSerialized.TmdbAPIErrorEnum.UNRECOGNIZED
        }
    }

    private fun extractTmdbAPIError(tmdbOriginSerialized: TmdbMetadataSerialized) : TmdbAPIError {
        val  serializedError = tmdbOriginSerialized.tmdbAPIError
        return when(serializedError){
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_INTERNET -> TmdbAPIError.NoInternet()
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_TOOL_ERROR -> TmdbAPIError.ToolError()
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_DATA -> TmdbAPIError.NoData()
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_EXCEPTION -> TmdbAPIError.Exception(tmdbOriginSerialized.exceptionLocalizedMessage)
            TmdbMetadataSerialized.TmdbAPIErrorEnum.UNRECOGNIZED ->  TmdbAPIError.Unknown()
        }
    }

}