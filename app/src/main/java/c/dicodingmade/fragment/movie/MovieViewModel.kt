package c.dicodingmade.fragment.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import c.dicodingmade.BuildConfig
import c.dicodingmade.model.MovieResult
import c.dicodingmade.util.ViewStatusConnection
import c.dicodingmade.webservice.ApiService
import c.dicodingmade.webservice.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieViewModel : ViewModel() {
    private var movieViewModelJob = Job()
    private var coroutineScope = CoroutineScope(movieViewModelJob + Dispatchers.Main)
    private val _movies = MutableLiveData<List<MovieResult>>()
    val movies: LiveData<List<MovieResult>>
        get() = _movies
    private val _statusConnectionView = MutableLiveData<ViewStatusConnection>()
    val statusConnectionView: LiveData<ViewStatusConnection>
        get() = _statusConnectionView
    private val _refreshStatus = MutableLiveData<Boolean>()
    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    init {
        getMovieList()
    }

    fun onRefresh() {
        _refreshStatus.value = true
        getMovieList()
    }

    private fun getMovieList() {
        coroutineScope.launch {
            val services = RetrofitBuilder.getInstance(BuildConfig.BASE_URL_API)
                .create(ApiService::class.java)
            try {
                _statusConnectionView.value = ViewStatusConnection.LOADING
                val movieListResult = services.getMovieList(BuildConfig.TOKEN, "en-US").results
                _statusConnectionView.value = ViewStatusConnection.DONE
                _refreshStatus.value = false
                _movies.value = movieListResult

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
        movieViewModelJob.cancel()
    }
}