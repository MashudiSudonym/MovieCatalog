package c.dicodingmade.ui.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contentMovieUpcoming.ContentMovieUpcomingEntity
import c.dicodingmade.repository.ContentMovieUpcomingRepository
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val movieUpcomingDao = ApplicationDatabase.getDatabase(application)
    private val movieUpcomingRepository = ContentMovieUpcomingRepository(movieUpcomingDao)
    var movieUpcomingToday: ContentMovieUpcomingEntity? = null

    init {
        viewModelScope.launch {
            movieUpcomingRepository.refreshMovieUpcoming()
        }
    }

    fun movieUpcomingByDate(today: String) {
        viewModelScope.launch {
            movieUpcomingToday = movieUpcomingRepository.getMovieUpcomingByDate(today)
        }
    }
}