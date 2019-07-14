package c.m.dicodingmadefavorite.ui.moviefavorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import c.m.dicodingmadefavorite.database.ApplicationDatabase
import c.m.dicodingmadefavorite.domain.ContentResult
import c.m.dicodingmadefavorite.repository.FavoriteRepository
import c.m.dicodingmadefavorite.util.ViewStatusConnection

class MovieFavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteDao = ApplicationDatabase.getDatabase(application)
    private val favoriteRepository = FavoriteRepository(favoriteDao)
    lateinit var movieFavoriteList: LiveData<List<ContentResult>>

    private val _movieFavorites = MutableLiveData<List<ContentResult>>()
    val movieFavorites: LiveData<List<ContentResult>>
        get() = _movieFavorites

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
        getMovieFavorite()
    }

    private fun getMovieFavorite() {
        movieFavoriteList = favoriteRepository.getAllMovieFavorite()
        _refreshStatus.value = false
    }

    fun movieFavoriteData(favorite: List<ContentResult>) {
        if (favorite.isNullOrEmpty()) {
            _statusConnectionView.value = ViewStatusConnection.LOADING
            _statusConnectionView.value = ViewStatusConnection.ERROR
            _refreshStatus.value = false
        } else {
            _statusConnectionView.value = ViewStatusConnection.LOADING
            _statusConnectionView.value = ViewStatusConnection.DONE
            _refreshStatus.value = false
            _movieFavorites.value = favorite
        }
    }

    fun displayDetail(favorite: ContentResult) {
        _navigateToDetail.value = favorite
    }

    fun displayDetailComplete() {
        _navigateToDetail.value = null
    }

    fun onRefresh() {
        _refreshStatus.value = false
        getMovieFavorite()
    }
}