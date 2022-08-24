package eu.benayoun.androidmoviedatabase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.di.RetrofitTmdbRepositoryProvider
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor (@RetrofitTmdbRepositoryProvider private val tmdbRepository: TmdbRepository) : ViewModel(){
   fun getPopularMoviesFlow() =
       viewModelScope.launch{
           tmdbRepository.getPopularMoviesFlow().collect{ TmdbMovies : List<TmdbMovie> ->
                for(tmdbMovie in TmdbMovies){
                    LogUtils.v("Movie: ${tmdbMovie.title}")
                }
           }
   }
}