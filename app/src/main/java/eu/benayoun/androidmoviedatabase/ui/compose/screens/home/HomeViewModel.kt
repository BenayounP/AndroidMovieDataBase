package eu.benayoun.androidmoviedatabase.ui.compose.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.benayoun.androidmoviedatabase.data.repository.DataOrigin
import eu.benayoun.androidmoviedatabase.data.repository.TmdbMetaMovieList
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.data.source.TmdbAPIError
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
            tmdbRepository.getPopularMovieListFlow().flowOn(Dispatchers.IO).collect{ tmdbMetaMovieList : TmdbMetaMovieList ->
                // Log data origin and cause
                val logMessageBuilder = StringBuilder()
                when(val dataOrigin = tmdbMetaMovieList.dataOrigin){
                    is DataOrigin.Internet -> logMessageBuilder.append("Internet!")
                    is DataOrigin.Cache -> {
                        logMessageBuilder.append("Cache. Cause: ")
                        val tmdbAPIError = dataOrigin.tmdbAPIError
                        val cause : String = when(tmdbAPIError){
                            is TmdbAPIError.NoInternet -> "NoInternet"
                            is TmdbAPIError.ToolError -> "ToolError"
                            is TmdbAPIError.NoData -> "NoData"
                            is TmdbAPIError.Exception -> "Exception: ${tmdbAPIError.localizedMessage}"
                        }
                        logMessageBuilder.append(cause)
                    }
                }
                LogUtils.v(logMessageBuilder.toString())
                _movieListState.value=tmdbMetaMovieList.movieList
            }
        }
}