package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables.home

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeDimensions.Companion.padding1
import java.util.*

@Composable
fun OriginStatusComposable(tmdbMetadata: eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata,
                           modifier: Modifier = Modifier) {
    Row(modifier
        .fillMaxWidth()
        .background(getBackgroundColor(tmdbMetadata))
    ){
        Text(text = getMetadataText(tmdbMetadata),
            Modifier.fillMaxWidth().padding(horizontal = padding1),
            color = Color.White)
    }
}

@Composable
private fun getBackgroundColor(tmdbMetadata: eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata) : Color {
    return if (tmdbMetadata.tmdbSourceStatus is eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus.Cache){
        MaterialTheme.colorScheme.error
    }
    else
    {
        MaterialTheme.colorScheme.primary
    }
}

private fun getMetadataText(tmdbMetadata: eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata) : String{
    val textBuilder = StringBuilder()
    val tmdbOrigin = tmdbMetadata.tmdbSourceStatus
    when(tmdbOrigin){
        is TmdbSourceStatus.None ->  textBuilder.append("None (yet)")
        is TmdbSourceStatus.Processing -> textBuilder.append("Processing")
        is TmdbSourceStatus.Internet -> textBuilder.append("Origin: Internet!")
        is TmdbSourceStatus.Cache -> {
            textBuilder.append("Origin: Cache.\nCause: ")
            val tmdbAPIError = tmdbOrigin.tmdbAPIError
            val cause : String = when(tmdbAPIError){
                is TmdbAPIError.NoInternet -> "NoInternet"
                is TmdbAPIError.ToolError -> "ToolError"
                is TmdbAPIError.NoData -> "NoData"
                is TmdbAPIError.Exception -> "Exception: ${tmdbAPIError.localizedMessage}"
                is TmdbAPIError.Unknown -> "Unknown"
            }
            textBuilder.append(cause)
        }
        is TmdbSourceStatus.Unknown -> textBuilder.append("Unknown")
    }
    textBuilder.append("\nLast internet update: ${getReadableTimeStamp(tmdbMetadata.lastInternetSuccessTimeStamp)}")
    return textBuilder.toString()
}

private fun getReadableTimeStamp(timeStamp : Long): String{
    val cal: Calendar = Calendar.getInstance()
    cal.setTimeInMillis(timeStamp)
    return DateFormat.format("hh:mm:ss", cal).toString()
}