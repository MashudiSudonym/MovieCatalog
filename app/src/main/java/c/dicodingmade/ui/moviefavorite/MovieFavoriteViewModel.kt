package c.dicodingmade.ui.moviefavorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import c.dicodingmade.database.FavoriteDatabase
import c.dicodingmade.model.ContentResult
import c.dicodingmade.repository.FavoriteRepository
import c.dicodingmade.util.ViewStatusConnection

class MovieFavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
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
        _refreshStatus.value = true
        getMovieFavorite()
    }
}