package c.dicodingmade.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import c.dicodingmade.model.MovieResult
import c.dicodingmade.model.TvShowResult

class DetailViewModel(movieResultData: MovieResult, tvShowResultData: TvShowResult) : ViewModel() {
    private val _movies = MutableLiveData<MovieResult>()
    val movies: LiveData<MovieResult>
        get() = _movies

    private val _tvShows = MutableLiveData<TvShowResult>()
    val tvShows: LiveData<TvShowResult>
        get() = _tvShows

    init {
        _movies.value = movieResultData
        _tvShows.value = tvShowResultData
    }
}
