package c.dicodingmade.fragment.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import c.dicodingmade.BuildConfig
import c.dicodingmade.model.MovieResult
import c.dicodingmade.util.ApiStatusConnection
import c.dicodingmade.webservice.ApiService
import c.dicodingmade.webservice.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private var movieViewModelJob = Job()
    private var coroutineScope = CoroutineScope(movieViewModelJob + Dispatchers.Main)
    private val _movies = MutableLiveData<List<MovieResult>>()
    val movies: LiveData<List<MovieResult>>
        get() = _movies
    private val _status = MutableLiveData<ApiStatusConnection>()
    val status: LiveData<ApiStatusConnection>
        get() = _status

    init {
        getMovieList()
    }

    private fun getMovieList() {
        coroutineScope.launch {
            val services = RetrofitBuilder.getInstance(BuildConfig.BASE_URL_API)
                .create(ApiService::class.java)
            try {
                _status.value = ApiStatusConnection.LOADING
                val movieListResult = services.getMovieList(BuildConfig.TOKEN, "en-US").results
                _status.value = ApiStatusConnection.DONE
                _movies.value = movieListResult
            } catch (e: Exception) {
                Log.d("Error", e.localizedMessage)
                _status.value = ApiStatusConnection.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        movieViewModelJob.cancel()
    }
}