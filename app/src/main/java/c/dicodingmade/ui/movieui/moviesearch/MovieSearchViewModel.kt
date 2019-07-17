package c.dicodingmade.ui.movieui.moviesearch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.repository.ContentMovieSearchRepository
import c.dicodingmade.util.ViewStatusConnection
import org.koin.android.ext.android.inject

class MovieSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val contentMovieSearchRepository: ContentMovieSearchRepository by application.inject()
    var contentMovie: LiveData<List<ContentResult>> = contentMovieSearchRepository.contentMovieSearch

    private val _movies = MutableLiveData<List<ContentResult>>()
    val movies: LiveData<List<ContentResult>>
        get() = _movies

    private val _statusConnectionView = MutableLiveData<ViewStatusConnection>()
    val statusConnectionView: LiveData<ViewStatusConnection>
        get() = _statusConnectionView

    private val _navigateToDetail = MutableLiveData<ContentResult>()
    val navigateToDetail: LiveData<ContentResult>
        get() = _navigateToDetail

    init {
    }

    fun displayDetail(contentResult: ContentResult) {
        _navigateToDetail.value = contentResult
    }

    fun displayDetailComplete() {
        _navigateToDetail.value = null
    }

    fun contentMovieData(contentMovie: List<ContentResult>) {
        if (contentMovie.isNullOrEmpty()) {
            _statusConnectionView.value = ViewStatusConnection.SEARCH
        } else {
            _statusConnectionView.value = ViewStatusConnection.LOADING
            _movies.value = contentMovie
            _statusConnectionView.value = ViewStatusConnection.DONE
        }
    }
}