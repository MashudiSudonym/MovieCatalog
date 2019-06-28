package c.dicodingmade.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import c.dicodingmade.model.MovieResult
import c.dicodingmade.model.TvShowResult

class DetailViewModelFactory(private val movieResultData: MovieResult, private val tvShowResultData: TvShowResult) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(movieResultData, tvShowResultData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}