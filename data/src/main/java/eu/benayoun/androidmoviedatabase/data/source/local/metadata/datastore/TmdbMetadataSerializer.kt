package eu.benayoun.androidmoviedatabase.data.source.local.metadata.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import eu.benayoun.androidmoviedatabase.TmdbMetadataSerialized

import java.io.InputStream
import java.io.OutputStream

internal object TmdbMetadataSerializer: Serializer<TmdbMetadataSerialized> {
    override val defaultValue: TmdbMetadataSerialized
        get() = TmdbMetadataSerialized.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TmdbMetadataSerialized {
        try {
            return TmdbMetadataSerialized.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: TmdbMetadataSerialized, output: OutputStream) = t.writeTo(output)
}