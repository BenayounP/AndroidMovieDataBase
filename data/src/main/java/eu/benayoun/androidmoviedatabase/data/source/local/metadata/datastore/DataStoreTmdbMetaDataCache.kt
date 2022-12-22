package eu.benayoun.androidmoviedatabase.data.source.local.metadata.datastore

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import eu.benayoun.androidmoviedatabase.TmdbMetadataSerialized
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.data.source.local.metadata.TmdbMetaDataCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// this class wouldn't have exist without this response on StackOverflow
// https://stackoverflow.com/a/66101769/1859993


internal class DataStoreTmdbMetaDataCache(appContext: Context, filename:String="DataStoreTmdbMetaDataCache") :
    TmdbMetaDataCache {
    private val Context.tmdbOriginDataStore by dataStore(
        fileName = filename,
        serializer = TmdbMetadataSerializer,
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { TmdbMetadataSerialized.getDefaultInstance()}
        )
    )

    private  val tmdbOriginDataStore = appContext.tmdbOriginDataStore

    override suspend fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> = tmdbOriginDataStore.data.map{ tmdbMetadataSerialized: TmdbMetadataSerialized ->
        mapToTmdbMetadata(tmdbMetadataSerialized)
    }

    override suspend fun saveTmdbMetaData(tmdbMetadata: TmdbMetadata){
        tmdbOriginDataStore.updateData{tmdbMetadataSerialized: TmdbMetadataSerialized ->
            val builder = tmdbMetadataSerialized.toBuilder()

            if (tmdbMetadata.lastInternetSuccessTimestamp != TmdbMetadata.INVALID_TIMESTAMP){
                builder.lastInternetSuccessTimestamp=tmdbMetadata.lastInternetSuccessTimestamp
            }

            val tmdbSourceStatus = tmdbMetadata.tmdbSourceStatus
            if (tmdbSourceStatus is TmdbSourceStatus.Cache  ){
                processTmdbAPIError(tmdbSourceStatus.tmdbAPIError,builder)
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
            is TmdbSourceStatus.SerializationProblem -> TmdbMetadataSerialized.TmdbSourceEnum.UNRECOGNIZED
        }

    }


    private fun mapToTmdbMetadata(tmdbMetadataSerialized: TmdbMetadataSerialized): TmdbMetadata{
        val tmdbSourceStatus = mapToSourceStatus(tmdbMetadataSerialized)
        val serializedLastInternetSuccessTimestamp = tmdbMetadataSerialized.lastInternetSuccessTimestamp
        val lastInternetSuccessTimestamp = when(serializedLastInternetSuccessTimestamp){
            0L -> TmdbMetadata.INVALID_TIMESTAMP // because 0 is the default value for a Long in a DataStore
            else -> serializedLastInternetSuccessTimestamp
        }
        return TmdbMetadata(tmdbSourceStatus,lastInternetSuccessTimestamp)
    }

    private fun mapToSourceStatus(tmdbMetadataSerialized: TmdbMetadataSerialized) : TmdbSourceStatus {
        return when(tmdbMetadataSerialized.tmdbSourceEnum){
            TmdbMetadataSerialized.TmdbSourceEnum.TMDB_ORIGIN_NONE -> TmdbSourceStatus.None
            TmdbMetadataSerialized.TmdbSourceEnum.TMDB_ORIGIN_INTERNET -> TmdbSourceStatus.Internet
            TmdbMetadataSerialized.TmdbSourceEnum.TMDB_ORIGIN_CACHE -> {
                TmdbSourceStatus.Cache(
                    extractTmdbAPIError(tmdbMetadataSerialized)
                )
            }
            TmdbMetadataSerialized.TmdbSourceEnum.UNRECOGNIZED -> TmdbSourceStatus.SerializationProblem
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

    private fun extractTmdbAPIError(tmdbOriginSerialized: TmdbMetadataSerialized) : TmdbAPIError {
        val  serializedError = tmdbOriginSerialized.tmdbAPIError
        return when(serializedError){
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_INTERNET -> TmdbAPIError.NoInternet
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_TOOL_ERROR -> TmdbAPIError.ToolError
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_NO_DATA -> TmdbAPIError.NoData
            TmdbMetadataSerialized.TmdbAPIErrorEnum.TMDB_API_ERROR_EXCEPTION -> TmdbAPIError.Exception(tmdbOriginSerialized.exceptionLocalizedMessage)
            TmdbMetadataSerialized.TmdbAPIErrorEnum.UNRECOGNIZED ->  TmdbAPIError.Unknown
        }
    }

}