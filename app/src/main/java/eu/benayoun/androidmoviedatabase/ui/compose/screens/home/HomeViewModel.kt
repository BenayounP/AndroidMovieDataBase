package eu.benayoun.androidmoviedatabase.ui.compose.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.di.RetrofitTmdbRepositoryProvider
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (@RetrofitTmdbRepositoryProvider private val tmdbRepository: TmdbRepository) : ViewModel(){
    private val _movieListState = MutableStateFlow<List<TmdbMovie>>(listOf())
    val movieListState : StateFlow<List<TmdbMovie>>
    get() = _movieListState

    fun getPopularMoviesFlow() =
        viewModelScope.launch{
            tmdbRepository.getPopularMovieListFlow().flowOn(Dispatchers.IO).collect{ tmdbMovies : List<TmdbMovie> ->
                _movieListState.value=tmdbMovies
            }
        }
}