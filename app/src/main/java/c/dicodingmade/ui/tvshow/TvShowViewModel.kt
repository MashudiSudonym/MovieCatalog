package c.dicodingmade.ui.tvshow

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import c.dicodingmade.database.contentTvShow.ContentTvShowDatabase
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.repository.ContentTvShowRepository
import c.dicodingmade.util.ViewStatusConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvShowViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ContentTvShowDatabase.getDatabase(application)
    private val contentTvShowRepository = ContentTvShowRepository(database)
    lateinit var contentTvShow: LiveData<List<ContentResult>>

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
        _refreshStatus.value = true
        getTvShowList()
    }

    private fun getTvShowList() {
        deleteContentTvShow()
        contentTvShow = contentTvShowRepository.contentTvShow
        viewModelScope.launch {
            try {
                contentTvShowRepository.refreshContentTvShow()
            } catch (e: Throwable) {
                Log.e("Error", e.localizedMessage)
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

    private fun deleteContentTvShow() = viewModelScope.launch(Dispatchers.IO) {
        contentTvShowRepository.deleteContentTvShow()
    }
}