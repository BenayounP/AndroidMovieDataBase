package eu.benayoun.androidmoviedatabase.data.repository.cache.metadata.datastore

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import eu.benayoun.androidmoviedatabase.TmdbMetadataSerialized
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

internal class DataStoreTmdbMetaDataCache(appContext: Context) :
    TmdbMetaDataCache {
    private  val tmdbOriginDataStore = appContext.tmdbOriginDataStore

    override fun getTmdbMetaDataFlow(): Flow<eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata> = tmdbOriginDataStore.data.map{ tmdbMetadataSerialized: TmdbMetadataSerialized ->
        eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata(
            mapToOrigin(
                tmdbMetadataSerialized
            ), tmdbMetadataSerialized.lastInternetSuccessTimeStamp
        )
    }

    override suspend fun saveTmdbMetaData(tmdbMetadata: eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata){
        tmdbOriginDataStore.updateData{tmdbMetadataSerialized: TmdbMetadataSerialized ->
           val builder = tmdbMetadataSerialized.toBuilder()
            val tmdbOrigin = tmdbMetadata.tmdbOrigin
            when(tmdbOrigin){
                is eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin.Internet -> builder.lastInternetSuccessTimeStamp = System.currentTimeMillis()
                is eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin.Cache -> processTmdbAPIError(tmdbOrigin.tmdbAPIError,builder)
                is eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin.Unknown -> null // nothing to do here
            }
            builder.tmdbOriginEnum = extractTmdbSourceEnum(tmdbOrigin)
            builder.build()
        }
    }

    // internal cooking

    private fun extractTmdbSourceEnum(tmdbOrigin : eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin) : TmdbMetadataSerialized.TmdbOriginEnum{
        return when (tmdbOrigin){
            is eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin.Internet -> TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_INTERNET
            is eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin.Cache -> TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_CACHE
            is eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin.Unknown -> TmdbMetadataSerialized.TmdbOriginEnum.UNRECOGNIZED
        }

    }

    private fun mapToOrigin(tmdbMetadataSerialized: TmdbMetadataSerialized) : eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin {
        return when(tmdbMetadataSerialized.tmdbOriginEnum){
            TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_INTERNET -> eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin.Internet()
            TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_CACHE -> {
                eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin.Cache(
                    extractTmdbAPIError(tmdbMetadataSerialized)
                )
            }
            TmdbMetadataSerialized.TmdbOriginEnum.UNRECOGNIZED -> eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin.Unknown()
        }
    }

    private fun processTmdbAPIError(tmdbAPIError: eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError, builder :  TmdbMetadataSerialized.Builder) {
        when(tmdbAPIError){
            is eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError.ToolError -> builder.tmdbAPIError = TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_TOOL_ERROR
            is eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError.Exception -> {
                builder.tmdbAPIError = TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_EXCEPTION
                builder.exceptionLocalizedMessage = tmdbAPIError.localizedMessage
            }
            is eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError.NoData -> TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_DATA
            is eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError.NoInternet -> TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_INTERNET
            is eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError.Unknown -> TmdbMetadataSerialized.TmdbAPIErrorEnum.UNRECOGNIZED
        }
    }

    private fun extractTmdbAPIError(tmdbOriginSerialized: TmdbMetadataSerialized) : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError {
        val  serializedError = tmdbOriginSerialized.tmdbAPIError
        return when(serializedError){
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_INTERNET -> eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError.NoInternet()
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_TOOL_ERROR -> eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError.ToolError()
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_DATA -> eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError.NoData()
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_EXCEPTION -> eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError.Exception(tmdbOriginSerialized.exceptionLocalizedMessage)
            TmdbMetadataSerialized.TmdbAPIErrorEnum.UNRECOGNIZED ->  eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError.Unknown()
        }
    }

}