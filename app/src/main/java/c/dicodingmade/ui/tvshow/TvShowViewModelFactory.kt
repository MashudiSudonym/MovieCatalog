package c.dicodingmade.ui.tvshow

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TvShowViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TvShowViewModel::class.java)) {
            return TvShowViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}