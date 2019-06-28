package c.dicodingmade.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import c.dicodingmade.model.MovieResult
import c.dicodingmade.model.TvShowResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DetailViewModel(movieResultData: MovieResult, tvShowResultData: TvShowResult) : ViewModel() {
    private var detailViewModelJob = Job()
    private var coroutneScope = (detailViewModelJob + Dispatchers.Main)

    // Movie Data
    private val _movies = MutableLiveData<MovieResult>()
    val movies: LiveData<MovieResult>
        get() = _movies

    // Tv Show Data
    private val _tvShows = MutableLiveData<TvShowResult>()
    val tvShows: LiveData<TvShowResult>
        get() = _tvShows

    init {
        _movies.value = movieResultData
        _tvShows.value = tvShowResultData
    }
}
