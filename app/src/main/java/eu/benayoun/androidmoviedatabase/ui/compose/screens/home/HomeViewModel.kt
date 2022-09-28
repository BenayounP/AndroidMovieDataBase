package eu.benayoun.androidmoviedatabase.ui.compose.screens.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.benayoun.androidmoviedatabase.data.di.DefaultTmdbRepositoryWithFakeDataSourceProvider
import eu.benayoun.androidmoviedatabase.data.di.FakeTmdbDataSourceProvider
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbUpdateStatus
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.data.source.network.FakeTmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor (@DefaultTmdbRepositoryWithFakeDataSourceProvider private val tmdbRepository: TmdbRepository,
                                         @FakeTmdbDataSourceProvider private val fakeTmdbDataSource: FakeTmdbDataSource
                                         ) : ViewModel(), DefaultLifecycleObserver {
    private val _movieListState = MutableStateFlow<List<TmdbMovie>>(listOf())
    val movieListState : StateFlow<List<TmdbMovie>>
    get() = _movieListState

    private val _tmdbMetadataState = MutableStateFlow(TmdbMetadata())
    val tmdbMetadataState : StateFlow<TmdbMetadata>
    get() = _tmdbMetadataState

    private val _tmdbUpdateStatus = MutableStateFlow<TmdbUpdateStatus>(TmdbUpdateStatus.Off())
    val tmdbUpdateStatus : StateFlow<TmdbUpdateStatus>
    get() = _tmdbUpdateStatus

    var fakeResultIsSuccess: Boolean = true

    init{
        getFlows()
        fakeTmdbDataSource.setDelayinMs(1000)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        updateTmdbMovies()
    }

    fun updateTmdbMovies(){
        if (_tmdbUpdateStatus.value is TmdbUpdateStatus.Off){
            when (fakeResultIsSuccess){
                true-> fakeTmdbDataSource.setSuccessResponse()
                false -> fakeTmdbDataSource.setExceptionErrorResponse("FUCK")
            }
            fakeResultIsSuccess=!fakeResultIsSuccess
            tmdbRepository.updateTmdbMovies()
        }
    }

    // INTERNAL COOKING

    private fun getFlows() {
        viewModelScope.launch {
            tmdbRepository.getTmdbUpdateStatusFlow().flowOn(Dispatchers.IO)
                .collect { tmdbUpdateStatus: TmdbUpdateStatus ->
                    _tmdbUpdateStatus.value = tmdbUpdateStatus
                }
        }
        viewModelScope.launch {
            tmdbRepository.getPopularMovieListFlow().flowOn(Dispatchers.IO)
                .collect { tmdbMovieList: List<TmdbMovie> ->
                    _movieListState.value = tmdbMovieList
                }
        }
        viewModelScope.launch {
            tmdbRepository.getTmdbMetaDataFlow().flowOn(Dispatchers.IO)
                .collect { tmdbMetadata: TmdbMetadata ->
                    LogUtils.v("new state in view model: ${tmdbMetadata.tmdbSourceStatus}")
                    _tmdbMetadataState.value = tmdbMetadata
                }
        }
    }
}