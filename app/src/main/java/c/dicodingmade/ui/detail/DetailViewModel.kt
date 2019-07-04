package c.dicodingmade.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import c.dicodingmade.database.Favorite
import c.dicodingmade.database.FavoriteDatabase
import c.dicodingmade.model.MovieResult
import c.dicodingmade.model.TvShowResult
import c.dicodingmade.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(movieResultData: MovieResult, tvShowResultData: TvShowResult, application: Application) :
    AndroidViewModel(application) {
    private val favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
    private val favoriteRepository = FavoriteRepository(favoriteDao)
    private val favorite = MutableLiveData<Favorite>()

    private val _movies = MutableLiveData<MovieResult>()
    val movies: LiveData<MovieResult>
        get() = _movies

    private val _tvShows = MutableLiveData<TvShowResult>()
    val tvShows: LiveData<TvShowResult>
        get() = _tvShows

    private val _favoriteStatus = MutableLiveData<Boolean>()
    val favoriteStatus: LiveData<Boolean>
        get() = _favoriteStatus

    init {
        _movies.value = movieResultData
        _tvShows.value = tvShowResultData
        _favoriteStatus.value = false
    }

    fun getFavoriteStatus(status: Boolean) {
        _favoriteStatus.value = status

        viewModelScope.launch(Dispatchers.IO) {
            // Check movie or tv show
            val contentCategory = when (_movies.value?.id) {
                0 -> false
                else -> true
            }

            // Add data to favorite entity
            val favorite = Favorite().apply {
                isFavorite = status
                isMovie = contentCategory
                backdropPath = _movies.value?.backdropPath as String
                id = _movies.value?.id as Int
                title = _movies.value?.title as String
                posterPath = _movies.value?.posterPath as String
                voteAverage = _movies.value?.voteAverage as Double
                overview = _movies.value?.overview as String
                releaseDate = _movies.value?.releaseDate as String
            }

            insert(favorite)
        }
    }

    private fun insert(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        favoriteRepository.insert(favorite)
    }
}
