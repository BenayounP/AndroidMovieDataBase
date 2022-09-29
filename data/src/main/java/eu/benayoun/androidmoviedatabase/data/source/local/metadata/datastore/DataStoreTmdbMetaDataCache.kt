package eu.benayoun.androidmoviedatabase.data.source.local.metadata.datastore

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import eu.benayoun.androidmoviedatabase.TmdbMetadataSerialized
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.data.source.local.metadata.TmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.utils.LogUtils
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

    override fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> = tmdbOriginDataStore.data.map{ tmdbMetadataSerialized: TmdbMetadataSerialized ->
        TmdbMetadata(
            mapToSourceStatus(
                tmdbMetadataSerialized
            ), tmdbMetadataSerialized.lastInternetSuccessTimeStamp
        )
    }

    override suspend fun saveTmdbMetaData(tmdbMetadata: TmdbMetadata){
        LogUtils.v("Save data in data store: ${tmdbMetadata.tmdbSourceStatus.toString()}")
        tmdbOriginDataStore.updateData{tmdbMetadataSerialized: TmdbMetadataSerialized ->
            val builder = tmdbMetadataSerialized.toBuilder()
            val tmdbSourceStatus = tmdbMetadata.tmdbSourceStatus
            when(tmdbSourceStatus){
                is TmdbSourceStatus.None -> null // nothing to do here
                is TmdbSourceStatus.Internet -> builder.lastInternetSuccessTimeStamp = System.currentTimeMillis()
                is TmdbSourceStatus.Cache -> processTmdbAPIError(tmdbSourceStatus.tmdbAPIError,builder)
                is TmdbSourceStatus.Unknown -> null // nothing to do here
            }
            builder.tmdbSourceEnum = extractTmdbSourceEnum(tmdbSourceStatus)
            builder.build()
        }
    }

    // internal cooking

    private fun extractTmdbSourceEnum(tmdbSourceStatus : TmdbSourceStatus) : TmdbMetadataSerialized.TmdbSourceEnum{
        return when (tmdbSourceStatus){
            is TmdbSourceStatus.None -> TmdbMetadataSerialized.TmdbSourceEnum.TMDB_ORIGIN_NONE
            is TmdbSourceStatus.Internet -> TmdbMetadataSerialized.TmdbSourceEnum.TMDB_ORIGIN_INTERNET
            is TmdbSourceStatus.Cache -> TmdbMetadataSerialized.TmdbSourceEnum.TMDB_ORIGIN_CACHE
            is TmdbSourceStatus.Unknown -> TmdbMetadataSerialized.TmdbSourceEnum.UNRECOGNIZED
        }

    }

    private fun mapToSourceStatus(tmdbMetadataSerialized: TmdbMetadataSerialized) : TmdbSourceStatus {
        return when(tmdbMetadataSerialized.tmdbSourceEnum){
            TmdbMetadataSerialized.TmdbSourceEnum.TMDB_ORIGIN_NONE -> TmdbSourceStatus.None()
            TmdbMetadataSerialized.TmdbSourceEnum.TMDB_ORIGIN_INTERNET -> TmdbSourceStatus.Internet()
            TmdbMetadataSerialized.TmdbSourceEnum.TMDB_ORIGIN_CACHE -> {
                TmdbSourceStatus.Cache(
                    extractTmdbAPIError(tmdbMetadataSerialized)
                )
            }
            TmdbMetadataSerialized.TmdbSourceEnum.UNRECOGNIZED -> TmdbSourceStatus.Unknown()
        }
    }

    private fun processTmdbAPIError(tmdbAPIError: TmdbAPIError, builder :  TmdbMetadataSerialized.Builder) {
        when(tmdbAPIError){
            is TmdbAPIError.ToolError -> builder.tmdbAPIError = TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_TOOL_ERROR
            is TmdbAPIError.Exception -> {
                builder.tmdbAPIError = TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_EXCEPTION
                builder.exceptionLocalizedMessage = tmdbAPIError.localizedMessage
            }
            is TmdbAPIError.NoData -> builder.tmdbAPIError =TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_DATA
            is TmdbAPIError.NoInternet -> builder.tmdbAPIError =TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_INTERNET
            is TmdbAPIError.Unknown -> builder.tmdbAPIError =TmdbMetadataSerialized.TmdbAPIErrorEnum.UNRECOGNIZED
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