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
import androidx.compose.ui.res.stringResource
import eu.benayoun.androidmoviedatabase.R
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbUpdateStatus
import eu.benayoun.androidmoviedatabase.ui.theme.BackgroundAndContentColor
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeColors
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeDimensions.Companion.padding1
import java.util.*

@Composable
fun UpdateStatusComposable(
    tmdbUpdateStatus : TmdbUpdateStatus,
    tmdbMetadata: TmdbMetadata,
    modifier: Modifier = Modifier) {
    var backgroundAndContentColor = getBackgroundAndContentColor(tmdbUpdateStatus, tmdbMetadata)
    Row(
        modifier
            .fillMaxWidth()
            .background(backgroundAndContentColor.background)
    ){
        Text(text = getMetadataText(tmdbUpdateStatus,tmdbMetadata),
            Modifier
                .fillMaxWidth()
                .padding(horizontal = padding1),
            color = backgroundAndContentColor.content)
    }
}

@Composable
private fun getBackgroundAndContentColor(tmdbUpdateStatus: TmdbUpdateStatus,tmdbMetadata: TmdbMetadata) : BackgroundAndContentColor {
    if (tmdbUpdateStatus is TmdbUpdateStatus.Updating) return ComposeColors.updating()
    else
        return if (tmdbMetadata.tmdbSourceStatus is TmdbSourceStatus.Cache){
        ComposeColors.problem()
    }
    else
    {
        ComposeColors.success()
    }
}

@Composable
private fun getMetadataText(tmdbUpdateStatus : TmdbUpdateStatus, tmdbMetadata: TmdbMetadata) : String{
    val textBuilder = StringBuilder()
    if (tmdbUpdateStatus is TmdbUpdateStatus.Updating) textBuilder.append("UPDATING\n\n")
    else {
        val tmdbOrigin = tmdbMetadata.tmdbSourceStatus
        when (tmdbOrigin) {
            is TmdbSourceStatus.None -> textBuilder.append(stringResource(R.string.source_status_none))
            is TmdbSourceStatus.Internet -> null // nothing to do
            is TmdbSourceStatus.Cache -> {
                textBuilder.append(stringResource(R.string.source_status_cache))
                val cause: String = when (val tmdbAPIError = tmdbOrigin.tmdbAPIError) {
                    is TmdbAPIError.NoInternet -> stringResource(R.string.source_status_no_internet)
                    is TmdbAPIError.ToolError -> stringResource(R.string.source_status_tool_error)
                    is TmdbAPIError.NoData -> stringResource(R.string.source_status_no_data)
                    is TmdbAPIError.Exception -> stringResource(R.string.source_status_exception,tmdbAPIError.localizedMessage)
                    is TmdbAPIError.Unknown -> stringResource(R.string.source_status_unknown)
                }
                textBuilder.append(cause+ "\n")
            }
            is TmdbSourceStatus.Unknown -> textBuilder.append(stringResource(R.string.source_status_unknown))
        }
        textBuilder.append(stringResource(R.string.source_status_last_update,getReadableTimeStamp(tmdbMetadata.lastInternetSuccessTimeStamp)))
    }
    return textBuilder.toString()
}

private fun getReadableTimeStamp(timeStamp : Long): String{
    val cal: Calendar = Calendar.getInstance()
    cal.setTimeInMillis(timeStamp)
    return DateFormat.format("hh:mm", cal).toString()
}