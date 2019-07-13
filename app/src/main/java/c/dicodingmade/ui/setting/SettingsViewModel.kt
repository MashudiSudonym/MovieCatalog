package c.dicodingmade.ui.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contentmovieupcoming.ContentUpcomingByDateEntity
import c.dicodingmade.repository.ContentMovieUpcomingRepository

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val movieUpcomingDao = ApplicationDatabase.getDatabase(application)
    private val movieUpcomingRepository = ContentMovieUpcomingRepository(movieUpcomingDao)
    var contentList: LiveData<List<ContentUpcomingByDateEntity>> = movieUpcomingRepository.contentUpcomingByDate
    var movies = MutableLiveData<List<ContentUpcomingByDateEntity>>()

    fun getContent(contentList: List<ContentUpcomingByDateEntity>) {
        movies.value = contentList
    }
}