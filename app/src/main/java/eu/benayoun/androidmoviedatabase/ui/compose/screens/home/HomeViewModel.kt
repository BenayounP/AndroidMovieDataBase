package eu.benayoun.androidmoviedatabase.ui.compose.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.benayoun.androidmoviedatabase.data.di.DefaultTmdbRepositoryProvider
import eu.benayoun.androidmoviedatabase.data.di.DefaultTmdbRepositoryWithFalseDataSourceProvider
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbUpdateStatus
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.utils.LogUtils
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

    private val _tmdbMetadataState = MutableStateFlow(TmdbMetadata())
    val tmdbMetadataState : StateFlow<TmdbMetadata>
    get() = _tmdbMetadataState

    private val _tmdbUpdateStatus = MutableStateFlow<TmdbUpdateStatus>(TmdbUpdateStatus.Off())
    val tmdbUpdateStatus : StateFlow<TmdbUpdateStatus>
    get() = _tmdbUpdateStatus

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

    fun getTmdbUpdateStatusFlow() =
        viewModelScope.launch {
            tmdbRepository.getTmdbUpdateStatusFlow().flowOn(Dispatchers.IO).collect{
                tmdbUpdateStatus : TmdbUpdateStatus ->
                _tmdbUpdateStatus.value = tmdbUpdateStatus
            }
        }

    fun updateTmdbMovies(){
        if (_tmdbUpdateStatus.value is TmdbUpdateStatus.Off) tmdbRepository.updateTmdbMovies()
    }
}