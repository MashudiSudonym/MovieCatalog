package c.dicodingmade.fragment.tvshow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import c.dicodingmade.BuildConfig
import c.dicodingmade.model.TvShowResult
import c.dicodingmade.util.ApiStatusConnection
import c.dicodingmade.webservice.ApiService
import c.dicodingmade.webservice.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TvShowViewModel : ViewModel() {
    private var tvShowViewModelJob = Job()
    private var coroutineScope = CoroutineScope(tvShowViewModelJob + Dispatchers.Main)
    private val _tvShows = MutableLiveData<List<TvShowResult>>()
    val tvShows: LiveData<List<TvShowResult>>
        get() = _tvShows
    private val _status = MutableLiveData<ApiStatusConnection>()
    val status: LiveData<ApiStatusConnection>
        get() = _status

    init {
        getTvShowList()
    }

    private fun getTvShowList() {
        coroutineScope.launch {
            val services = RetrofitBuilder.getInstance(BuildConfig.BASE_URL_API)
                .create(ApiService::class.java)
            try {
                _status.value = ApiStatusConnection.LOADING
                val tvShowListResult = services.getTvShowList(BuildConfig.TOKEN, "en-US").results
                _status.value = ApiStatusConnection.DONE
                _tvShows.value = tvShowListResult
            } catch (e: Exception) {
                Log.d("Error", e.localizedMessage)
                _status.value = ApiStatusConnection.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        tvShowViewModelJob.cancel()
    }
}