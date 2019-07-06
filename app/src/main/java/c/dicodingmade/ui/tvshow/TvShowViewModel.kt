package c.dicodingmade.ui.tvshow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import c.dicodingmade.BuildConfig
import c.dicodingmade.model.ContentResult
import c.dicodingmade.network.ApiService
import c.dicodingmade.network.RetrofitBuilder
import c.dicodingmade.util.ViewStatusConnection
import kotlinx.coroutines.launch
import retrofit2.HttpException

class TvShowViewModel : ViewModel() {
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

    fun displaDetailComplete() {
        _navigateToDetail.value = null
    }

    fun onRefresh() {
        _refreshStatus.value = true
        getTvShowList()
    }

    private fun getTvShowList() {
        viewModelScope.launch {
            val services = RetrofitBuilder.getInstance(BuildConfig.BASE_URL_API)
                .create(ApiService::class.java)
            try {
                _statusConnectionView.value = ViewStatusConnection.LOADING
                val tvShowListResult = services.getTvShowList(BuildConfig.TOKEN, "en-US").results
                _statusConnectionView.value = ViewStatusConnection.DONE
                _refreshStatus.value = false
                _tvShows.value = tvShowListResult
            } catch (e: Throwable) {
                Log.e("Error", e.localizedMessage)
                _statusConnectionView.value = ViewStatusConnection.ERROR
                _refreshStatus.value = false
            } catch (e: HttpException) {
                Log.e("Http Error", e.message())
                _statusConnectionView.value = ViewStatusConnection.ERROR
                _refreshStatus.value = false
            }
        }
    }
}