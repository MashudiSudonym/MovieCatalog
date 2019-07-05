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
import kotlinx.coroutines.withContext

class DetailViewModel(movieResultData: MovieResult, tvShowResultData: TvShowResult, application: Application) :
    AndroidViewModel(application) {
    private val favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
    private val favoriteRepository = FavoriteRepository(favoriteDao)

    private val _movies = MutableLiveData<MovieResult>()
    val movies: LiveData<MovieResult>
        get() = _movies

    private val _tvShows = MutableLiveData<TvShowResult>()
    val tvShows: LiveData<TvShowResult>
        get() = _tvShows

    private val _favoriteStatus = MutableLiveData<Boolean>()
    val favoriteStatus: LiveData<Boolean>
        get() = _favoriteStatus

    private val _showSnackBar = MutableLiveData<Boolean>()
    val showSnackBar: LiveData<Boolean>
        get() = _showSnackBar

    init {
        loadDataFromArgs(movieResultData, tvShowResultData)
        checkContentCategory()
    }

    fun getFavoriteStatus(status: Boolean) {
        _favoriteStatus.value = status
        _showSnackBar.value = status

        when (status) {
            true -> addFavoriteContent(status)
            false -> deleteFavoriteContent()
        }
    }

    private fun deleteFavoriteContent() {
        when (_movies.value?.id) {
            0 -> {
                deleteById(_tvShows.value?.id as Int)
                _favoriteStatus.value = false
            }
            else -> {
                deleteById(_movies.value?.id as Int)
                _favoriteStatus.value = false
            }
        }
    }

    private fun loadDataFromArgs(
        movieResultData: MovieResult,
        tvShowResultData: TvShowResult
    ) {
        _movies.value = movieResultData
        _tvShows.value = tvShowResultData
    }

    private fun checkContentCategory() {
        when (_movies.value?.id) {
            0 -> tvShowFavoriteStatus()
            else -> movieFavoriteStatus()
        }
    }

    private fun movieFavoriteStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val dataFromDatabase = favoriteRepository.getFavoriteById(_movies.value?.id as Int)
            if (dataFromDatabase?.id == _movies.value?.id) {
                withContext(Dispatchers.Main) { _favoriteStatus.value = dataFromDatabase?.isFavorite as Boolean }
            } else {
                withContext(Dispatchers.Main) { _favoriteStatus.value = false }
            }
        }
    }

    private fun tvShowFavoriteStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val dataFromDatabase = favoriteRepository.getFavoriteById(_tvShows.value?.id as Int)
            if (dataFromDatabase?.id == _tvShows.value?.id) {
                withContext(Dispatchers.Main) { _favoriteStatus.value = dataFromDatabase?.isFavorite as Boolean }
            } else {
                withContext(Dispatchers.Main) { _favoriteStatus.value = false }
            }
        }
    }

    private fun addFavoriteContent(status: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val contentMovie = when (_movies.value?.id) {
                0 -> false
                else -> true
            }

            val contentTvShow = when (_tvShows.value?.id) {
                0 -> false
                else -> true
            }

            // Add data to favorite entity
            val favoriteMovie = Favorite().apply {
                isFavorite = status
                isMovie = contentMovie
                isTvShow = contentTvShow
                backdropPath = _movies.value?.backdropPath as String
                id = _movies.value?.id as Int
                title = _movies.value?.title as String
                posterPath = _movies.value?.posterPath as String
                voteAverage = _movies.value?.voteAverage as Double
                overview = _movies.value?.overview as String
                releaseDate = _movies.value?.releaseDate as String
            }

            val favoriteTvShow = Favorite().apply {
                isFavorite = status
                isMovie = contentMovie
                isTvShow = contentTvShow
                backdropPath = _tvShows.value?.backdropPath as String
                id = _tvShows.value?.id as Int
                name = _tvShows.value?.name as String
                posterPath = _tvShows.value?.posterPath as String
                voteAverage = _tvShows.value?.voteAverage as Double
                overview = _tvShows.value?.overview as String
                firstAirDate = _tvShows.value?.firstAirDate as String
            }

            when (_movies.value?.id) {
                0 -> insert(favoriteTvShow)
                else -> insert(favoriteMovie)
            }
        }
    }

    private fun insert(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        favoriteRepository.insert(favorite)
    }

    private fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        favoriteRepository.deleteFavoriteById(id)
    }
}
