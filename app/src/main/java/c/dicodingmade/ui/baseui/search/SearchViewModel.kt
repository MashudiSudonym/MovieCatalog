package c.dicodingmade.ui.baseui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.repository.ContentMovieSearchRepository
import c.dicodingmade.repository.ContentTvShowSearchRepository
import c.dicodingmade.util.ViewStatusConnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ApplicationDatabase.getDatabase(application)
    private val contentMovieSearchRepository = ContentMovieSearchRepository(database)
    private val contentTvShowSearchRepository = ContentTvShowSearchRepository(database)

    private val _statusConnectionView = MutableLiveData<ViewStatusConnection>()
    val statusConnectionView: LiveData<ViewStatusConnection>
        get() = _statusConnectionView

    private val _refreshStatus = MutableLiveData<Boolean>()
    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    init {
        _statusConnectionView.value = ViewStatusConnection.SEARCH
    }

    fun onQuery(value: String?): Boolean {
        _statusConnectionView.value = ViewStatusConnection.LOADING
        viewModelScope.launch {
            value?.let {
                delay(500)
                if (it.isEmpty()) {
                    _statusConnectionView.value = ViewStatusConnection.SEARCH
                } else {
                    contentMovieSearchRepository.getMovieSearch(it)
                    contentTvShowSearchRepository.getTvShowSearch(it)
                    _statusConnectionView.value = ViewStatusConnection.DONE
                }
            }
        }
        return true
    }

    fun onRefresh() {
        _refreshStatus.value = false
        _statusConnectionView.value = ViewStatusConnection.LOADING
    }
}