package eu.benayoun.androidmoviedatabase.ui.compose.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.di.DefaultTmdbRepositoryProvider
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (@DefaultTmdbRepositoryProvider private val tmdbRepository: TmdbRepository) : ViewModel(){
    private val _movieListState = MutableStateFlow<List<TmdbMovie>>(listOf())
    val movieListState : StateFlow<List<TmdbMovie>>
    get() = _movieListState

    fun getPopularMoviesFlow() =
        viewModelScope.launch{
            tmdbRepository.getPopularMovieListFlow().flowOn(Dispatchers.IO).collect{ tmdbMovieList : List<TmdbMovie> ->
                _movieListState.value=tmdbMovieList
            }
        }

    fun getTmdbOriginFlow() =
        viewModelScope.launch {
            tmdbRepository.getTmdbOriginFlow().flowOn(Dispatchers.IO).collect {tmdbOrigin : TmdbOrigin ->
                // Log data origin and cause
                val logMessageBuilder = StringBuilder()
                when(tmdbOrigin){
                    is TmdbOrigin.Internet -> logMessageBuilder.append("Origin: Internet!")
                    is TmdbOrigin.Cache -> {
                        logMessageBuilder.append("Origin: Cache. Cause: ")
                        val tmdbAPIError = tmdbOrigin.tmdbAPIError
                        val cause : String = when(tmdbAPIError){
                            is TmdbAPIError.NoInternet -> "NoInternet"
                            is TmdbAPIError.ToolError -> "ToolError"
                            is TmdbAPIError.NoData -> "NoData"
                            is TmdbAPIError.Exception -> "Exception: ${tmdbAPIError.localizedMessage}"
                            is TmdbAPIError.Unknown -> "Unknown"
                        }
                        logMessageBuilder.append(cause)
                       // logMessageBuilder.append(". last internet timestamp: ${tmdbOrigin.}")
                    }
                    is TmdbOrigin.Unknown -> logMessageBuilder.append("Unknown")
                }
                LogUtils.v(logMessageBuilder.toString())
            }
        }
}