package eu.benayoun.androidmoviedatabase.data.repository.cache.metadata.datastore

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import eu.benayoun.androidmoviedatabase.TmdbMetadataSerialized
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
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
            val tmdbOrigin = tmdbMetadata.tmdbSourceStatus
            when(tmdbOrigin){
                is TmdbSourceStatus.None -> null // nothing to do here
                is TmdbSourceStatus.Internet -> builder.lastInternetSuccessTimeStamp = System.currentTimeMillis()
                is TmdbSourceStatus.Cache -> processTmdbAPIError(tmdbOrigin.tmdbAPIError,builder)
                is TmdbSourceStatus.Unknown -> null // nothing to do here
            }
            builder.tmdbOriginEnum = extractTmdbSourceEnum(tmdbOrigin)
            builder.build()
        }
    }

    // internal cooking

    private fun extractTmdbSourceEnum(tmdbSourceStatus : eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus) : TmdbMetadataSerialized.TmdbOriginEnum{
        return when (tmdbSourceStatus){
            is TmdbSourceStatus.None -> TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_NONE
            is TmdbSourceStatus.Internet -> TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_INTERNET
            is TmdbSourceStatus.Cache -> TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_CACHE
            is TmdbSourceStatus.Unknown -> TmdbMetadataSerialized.TmdbOriginEnum.UNRECOGNIZED
        }

    }

    private fun mapToOrigin(tmdbMetadataSerialized: TmdbMetadataSerialized) : eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus {
        return when(tmdbMetadataSerialized.tmdbOriginEnum){
            TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_NONE -> TmdbSourceStatus.None()
            TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_INTERNET -> TmdbSourceStatus.Internet()
            TmdbMetadataSerialized.TmdbOriginEnum.TMDB_ORIGIN_CACHE -> {
                TmdbSourceStatus.Cache(
                    extractTmdbAPIError(tmdbMetadataSerialized)
                )
            }
            TmdbMetadataSerialized.TmdbOriginEnum.UNRECOGNIZED -> TmdbSourceStatus.Unknown()
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