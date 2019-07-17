package c.dicodingmade.ui.tvshowui.tvshow

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.repository.ContentTvShowRepository
import c.dicodingmade.util.ViewStatusConnection
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class TvShowViewModel(application: Application) : AndroidViewModel(application) {
    private val contentTvShowRepository: ContentTvShowRepository by application.inject()
    var contentTvShow: LiveData<List<ContentResult>> = contentTvShowRepository.contentTvShow

    private val _tvShows = MutableLiveData<List<ContentResult>>()
    val tvShows: LiveData<List<ContentResult>>
        get() = _tvShows

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
        getTvShowList()
    }

    fun displayDetail(contentResult: ContentResult) {
        _navigateToDetail.value = contentResult
    }

    fun displayDetailComplete() {
        _navigateToDetail.value = null
    }

    fun onRefresh() {
        _refreshStatus.value = false
        getTvShowList()
    }

    private fun getTvShowList() {
        viewModelScope.launch {
            try {
                contentTvShowRepository.refreshContentTvShow()
            } catch (e: Throwable) {
                Log.e("Error", e.localizedMessage as String)
                _statusConnectionView.value = ViewStatusConnection.ERROR
                _refreshStatus.value = false
            }
        }
    }

    fun contentTvShowData(contentTvShow: List<ContentResult>) {
        if (contentTvShow.isNullOrEmpty()) {
            _statusConnectionView.value = ViewStatusConnection.LOADING
        } else {
            _statusConnectionView.value = ViewStatusConnection.LOADING
            _tvShows.value = contentTvShow
            _statusConnectionView.value = ViewStatusConnection.DONE
            _refreshStatus.value = false
        }
    }
}