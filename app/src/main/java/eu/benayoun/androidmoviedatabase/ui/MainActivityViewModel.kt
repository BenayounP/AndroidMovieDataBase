package eu.benayoun.androidmoviedatabase.ui

import android.graphics.Movie
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.benayoun.androidmoviedatabase.data.repository.TMDBRepository
import eu.benayoun.androidmoviedatabase.di.RetrofitTMDBRepositoryProvider
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import eu.pbenayoun.thatdmdbapp.repository.model.TMDBMovie
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor (@RetrofitTMDBRepositoryProvider private val TMDBRepository: TMDBRepository) : ViewModel(){
   fun getPopularMoviesFlow() =
       viewModelScope.launch{
           TMDBRepository.getPopularMoviesFlow().collect{TMDBMovies : List<TMDBMovie> ->
                for(TMDBMovie in TMDBMovies){
                    LogUtils.v("Movie: ${TMDBMovie.title}")
                }
           }
   }
}