package c.dicodingmade.ui.tvshow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import c.dicodingmade.BuildConfig
import c.dicodingmade.model.TvShowResult
import c.dicodingmade.util.ViewStatusConnection
import c.dicodingmade.webservice.ApiService
import c.dicodingmade.webservice.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException

class TvShowViewModel : ViewModel() {
    private var tvShowViewModelJob = Job()
    private var coroutineScope = CoroutineScope(tvShowViewModelJob + Dispatchers.Main)

    private val _tvShows = MutableLiveData<List<TvShowResult>>()
    val tvShows: LiveData<List<TvShowResult>>
        get() = _tvShows

    private val _statusConnectionView = MutableLiveData<ViewStatusConnection>()
    val statusConnectionView: LiveData<ViewStatusConnection>
        get() = _statusConnectionView

    private val _refreshStatus = MutableLiveData<Boolean>()
    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private val _navigateToDetail = MutableLiveData<TvShowResult>()
    val navigateToDetail: LiveData<TvShowResult>
        get() = _navigateToDetail

    init {
        getTvShowList()
    }

    fun displayDetail(tvShowResult: TvShowResult) {
        _navigateToDetail.value = tvShowResult
    }

    fun displaDetailComplete() {
        _navigateToDetail.value = null
    }

    fun onRefresh() {
        _refreshStatus.value = true
        getTvShowList()
    }

    private fun getTvShowList() {
        coroutineScope.launch {
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

    override fun onCleared() {
        super.onCleared()
        tvShowViewModelJob.cancel()
    }
}