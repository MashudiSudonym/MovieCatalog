package c.dicodingmade.ui.baseui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import c.dicodingmade.repository.ContentMovieSearchRepository
import c.dicodingmade.repository.ContentTvShowSearchRepository
import c.dicodingmade.util.ViewStatusConnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val contentMovieSearchRepository: ContentMovieSearchRepository by application.inject()
    private val contentTvShowSearchRepository: ContentTvShowSearchRepository by application.inject()

    private val _statusConnectionView = MutableLiveData<ViewStatusConnection>()
    val statusConnectionView: LiveData<ViewStatusConnection>
        get() = _statusConnectionView

    init {
        _statusConnectionView.value = ViewStatusConnection.SEARCH
    }

    fun onQuery(value: String?): Boolean {
        _statusConnectionView.value = ViewStatusConnection.LOADING
        viewModelScope.launch {
            value?.let {
                delay(300)
                if (it.isEmpty() || it.isBlank()) {
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
}