package c.dicodingmade.ui.tvshowui.tvshowfavorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.repository.FavoriteRepository
import c.dicodingmade.util.ViewStatusConnection

class TvShowFavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteDao = ApplicationDatabase.getDatabase(application)
    private val favoriteRepository = FavoriteRepository(favoriteDao)
    lateinit var tvShowFavoriteList: LiveData<List<ContentResult>>

    private val _tvShowFavorites = MutableLiveData<List<ContentResult>>()
    val tvShowFavorites: LiveData<List<ContentResult>>
        get() = _tvShowFavorites

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
        getTvShowFavorite()
    }

    private fun getTvShowFavorite() {
        tvShowFavoriteList = favoriteRepository.getAllTvShowFavorite()
        _refreshStatus.value = false
    }

    fun tvShowFavoriteData(favorite: List<ContentResult>) {
        if (favorite.isNullOrEmpty()) {
            _statusConnectionView.value = ViewStatusConnection.LOADING
            _statusConnectionView.value = ViewStatusConnection.ERROR
            _refreshStatus.value = false
        } else {
            _statusConnectionView.value = ViewStatusConnection.LOADING
            _statusConnectionView.value = ViewStatusConnection.DONE
            _refreshStatus.value = false
            _tvShowFavorites.value = favorite
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
        getTvShowFavorite()
    }
}