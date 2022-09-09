package eu.benayoun.androidmoviedatabase.ui.compose.screens.home

import android.text.format.DateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
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
import java.util.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor (@DefaultTmdbRepositoryProvider private val tmdbRepository: TmdbRepository) : ViewModel(){
    private val _movieListState = MutableStateFlow<List<TmdbMovie>>(listOf())
    val movieListState : StateFlow<List<TmdbMovie>>
    get() = _movieListState

    private val _tmdbMetadataState = MutableStateFlow(TmdbMetadata())
    val tmdbMetadataState : StateFlow<TmdbMetadata>
    get() = _tmdbMetadataState

    fun getPopularMoviesFlow() =
        viewModelScope.launch{
            tmdbRepository.getPopularMovieListFlow().flowOn(Dispatchers.IO).collect{ tmdbMovieList : List<TmdbMovie> ->
                _movieListState.value=tmdbMovieList
            }
        }

    fun getTmdbOriginFlow() =
        viewModelScope.launch {
            tmdbRepository.getTmdbMetaDataFlow().flowOn(Dispatchers.IO).collect {tmdbMetadata : TmdbMetadata ->
                _tmdbMetadataState.value = tmdbMetadata
            }
        }
}