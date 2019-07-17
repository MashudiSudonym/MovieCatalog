package c.dicodingmade.ui.tvshowui.tvshowsearch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.repository.ContentTvShowSearchRepository
import c.dicodingmade.util.ViewStatusConnection
import org.koin.android.ext.android.inject

class TvShowSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val contentTvShowSearchRepository: ContentTvShowSearchRepository by application.inject()
    var contentTvShow: LiveData<List<ContentResult>> = contentTvShowSearchRepository.contentTvShowSearch

    private val _tvShows = MutableLiveData<List<ContentResult>>()
    val tvShows: LiveData<List<ContentResult>>
        get() = _tvShows

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

    fun contentTvShowData(contentTvShow: List<ContentResult>) {
        if (contentTvShow.isNullOrEmpty()) {
            _statusConnectionView.value = ViewStatusConnection.SEARCH
        } else {
            _statusConnectionView.value = ViewStatusConnection.LOADING
            _tvShows.value = contentTvShow
            _statusConnectionView.value = ViewStatusConnection.DONE
        }
    }
}