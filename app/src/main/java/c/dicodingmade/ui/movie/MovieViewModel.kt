package c.dicodingmade.ui.movie

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.repository.ContentMovieRepository
import c.dicodingmade.util.ViewStatusConnection
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ApplicationDatabase.getDatabase(application)
    private val contentMovieRepository = ContentMovieRepository(database)
    var contentMovie: LiveData<List<ContentResult>> = contentMovieRepository.contentMovie

    private val _movies = MutableLiveData<List<ContentResult>>()
    val movies: LiveData<List<ContentResult>>
        get() = _movies

    private val _statusConnectionView = MutableLiveData<ViewStatusConnection>()
    val statusConnectionView: LiveData<ViewStatusConnection>
        get() = _statusConnectionView

    private val _refreshStatus = MutableLiveData<Boolean>()
    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private val _navigateToDetail = MutableLiveData<ContentResult>()
    val navigateToDetail: LiveData<ContentResult>
        get() = _navigateToDetail

    init {
        getMovieList()
    }

    fun displayDetail(contentResult: ContentResult) {
        _navigateToDetail.value = contentResult
    }

    fun displayDetailComplete() {
        _navigateToDetail.value = null
    }

    fun onRefresh() {
        _refreshStatus.value = true
        getMovieList()
    }

    private fun getMovieList() {
        viewModelScope.launch {
            try {
                contentMovieRepository.refreshContentMovie()
            } catch (e: Throwable) {
                Log.e("Error", e.localizedMessage as String)
                _statusConnectionView.value = ViewStatusConnection.ERROR
                _refreshStatus.value = false
            }
        }
    }

    fun contentMovieData(contentMovie: List<ContentResult>) {
        if (contentMovie.isNullOrEmpty()) {
            _statusConnectionView.value = ViewStatusConnection.LOADING
        } else {
            _statusConnectionView.value = ViewStatusConnection.LOADING
            _movies.value = contentMovie
            _statusConnectionView.value = ViewStatusConnection.DONE
            _refreshStatus.value = false
        }
    }
}