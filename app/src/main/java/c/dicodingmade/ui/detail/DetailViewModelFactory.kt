package c.dicodingmade.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import c.dicodingmade.domain.ContentResult

class DetailViewModelFactory(
    private val contentResultData: ContentResult,
    private val application: Application,
    private val isMovie: Boolean,
    private val isTvShow: Boolean
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(contentResultData, application, isMovie, isTvShow) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}