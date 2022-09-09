package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables.home

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin
import java.util.*

@Composable
fun OriginStatus(tmdbMetadata: TmdbMetadata,
    modifier: Modifier = Modifier) {
    Row(modifier.background(getBackgroundColor(tmdbMetadata))){
        Text(getMetadataText(tmdbMetadata))
    }

}

@Composable
private fun getBackgroundColor(tmdbMetadata: TmdbMetadata) : Color {
    return if (tmdbMetadata.tmdbOrigin is TmdbOrigin.Cache){
        MaterialTheme.colorScheme.error
    }
    else
    {
        MaterialTheme.colorScheme.background
    }
}

private fun getMetadataText(tmdbMetadata: TmdbMetadata) : String{
    val textBuilder = StringBuilder()
    val tmdbOrigin = tmdbMetadata.tmdbOrigin
    when(tmdbOrigin){
        is TmdbOrigin.Internet -> textBuilder.append("Origin: Internet!")
        is TmdbOrigin.Cache -> {
            textBuilder.append("Origin: Cache. Cause: ")
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
        is TmdbOrigin.Unknown -> textBuilder.append("Unknown")
    }
    textBuilder.append(". last internet timestamp: ${getReadableTimeStamp(tmdbMetadata.lastInternetSuccessTimeStamp)}")
    return textBuilder.toString()
}

private fun getReadableTimeStamp(timeStamp : Long): String{
    val cal: Calendar = Calendar.getInstance()
    cal.setTimeInMillis(timeStamp)
    return DateFormat.format("hh:mm:ss", cal).toString()
}