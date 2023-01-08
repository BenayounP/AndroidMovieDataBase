package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables.home

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import eu.benayoun.androidmoviedatabase.R
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbUpdateStatus
import eu.benayoun.androidmoviedatabase.ui.theme.BackgroundAndContentColor
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeColors
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeDimensions.padding1
import java.util.*

@Composable
fun UpdateStatusComposable(
    tmdbMovieList: List<TmdbMovie>,
    tmdbUpdateStatus : TmdbUpdateStatus,
    tmdbMetadata: TmdbMetadata,
    updateMovies: () -> Unit,
    modifier: Modifier = Modifier,
    textTestTag: String= "") {
    val backgroundAndContentColor = getBackgroundAndContentColor(tmdbUpdateStatus, tmdbMetadata)
    Row(
        modifier
            .padding(padding1)
            .fillMaxWidth()
            .background(
                color = backgroundAndContentColor.background,
                shape = RoundedCornerShape(padding1)
            )
            .clickable(
                onClick = updateMovies
            ),

        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        Text(text = getMetadataText(tmdbMovieList, tmdbUpdateStatus,tmdbMetadata),
            modifier = Modifier
                .padding(horizontal = padding1)
                .testTag(textTestTag),
            color = backgroundAndContentColor.content,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium)
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
private fun getMetadataText(tmdbMovieList: List<TmdbMovie>,tmdbUpdateStatus : TmdbUpdateStatus, tmdbMetadata: TmdbMetadata) : String{
    val metadataTextBuilder = StringBuilder()
    // UPDATING
    if (tmdbUpdateStatus is TmdbUpdateStatus.Updating) metadataTextBuilder.append(stringResource(R.string.update_status_updating))
    // UPDATED
    else {
        val tmdbSourceStatus = tmdbMetadata.tmdbSourceStatus
        // No movie to display
        if (tmdbMovieList.isEmpty()){
            metadataTextBuilder.append(stringResource(R.string.source_status_none)).append("\n\n")
            if (tmdbSourceStatus is TmdbSourceStatus.Cache || tmdbSourceStatus is TmdbSourceStatus.SerializationProblem) {
                metadataTextBuilder.append(stringResource(R.string.update_status_cause))
                    .append("\n")
                metadataTextBuilder.append(getRefreshErrorCause(tmdbSourceStatus)).append("\n\n")
            }
            metadataTextBuilder.append(stringResource(R.string.click_to_refresh))
        }
        // There is data
        else {
            // Data from internet or cache
            when (tmdbSourceStatus) {
                is TmdbSourceStatus.None -> metadataTextBuilder.append(stringResource(R.string.source_status_none))
                is TmdbSourceStatus.Internet -> null // nothing to do
                is TmdbSourceStatus.Cache,
                is TmdbSourceStatus.SerializationProblem-> {
                    metadataTextBuilder.append(stringResource(R.string.source_status_cache))
                        .append("\n")
                    metadataTextBuilder.append(stringResource(R.string.update_status_cause))
                        .append("\n")
                    metadataTextBuilder.append(getRefreshErrorCause(tmdbSourceStatus)).append("\n")
                }
            }
            if (tmdbMetadata.lastInternetSuccessTimestamp != TmdbMetadata.INVALID_TIMESTAMP) {
                metadataTextBuilder.append(
                    stringResource(R.string.source_status_last_update).plus(
                        getReadableTimeStamp(tmdbMetadata.lastInternetSuccessTimestamp)
                    )
                )
            }
        }
    }
    return metadataTextBuilder.toString()
}

// get string error only with TmdbSourceStatus.SerializationProblem and TmdbSourceStatus.Cache
@Composable
private fun getRefreshErrorCause(tmdbSourceStatus: TmdbSourceStatus) : String {
    var refreshErrorCause = ""

    if (tmdbSourceStatus is TmdbSourceStatus.SerializationProblem){
        refreshErrorCause =  stringResource(R.string.source_status_serialization_problem)
    }
    else if (tmdbSourceStatus is TmdbSourceStatus.Cache){
        val tmdbAPIError = tmdbSourceStatus.tmdbAPIError
        refreshErrorCause =  when (tmdbAPIError) {
            is TmdbAPIError.NoInternet -> stringResource(R.string.source_status_no_internet)
            is TmdbAPIError.ToolError -> stringResource(R.string.source_status_tool_error)
            is TmdbAPIError.NoData -> stringResource(R.string.source_status_no_data)
            is TmdbAPIError.Exception -> stringResource(R.string.source_status_exception).plus(
                tmdbAPIError.localizedMessage
            )
            is TmdbAPIError.Unknown -> stringResource(R.string.source_status_unknown)
        }
    }
    return refreshErrorCause
}

private fun getReadableTimeStamp(timeStamp : Long): String{
    val cal: Calendar = Calendar.getInstance()
    cal.timeInMillis = timeStamp
    return DateFormat.format("hh:mm", cal).toString()
}