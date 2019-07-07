package c.dicodingmade.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.favorite.FavoriteEntity
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    contentResultData: ContentResult,
    application: Application,
    isMovie: Boolean,
    isTvShow: Boolean
) :
    AndroidViewModel(application) {
    private val favoriteDao = ApplicationDatabase.getDatabase(application)
    private val favoriteRepository = FavoriteRepository(favoriteDao)
    private val contentIsMovie = isMovie
    private val contentIsTvShow = isTvShow

    private val _contents = MutableLiveData<ContentResult>()
    val contents: LiveData<ContentResult>
        get() = _contents

    private val _favoriteStatus = MutableLiveData<Boolean>()
    val favoriteStatus: LiveData<Boolean>
        get() = _favoriteStatus

    private val _showSnackBar = MutableLiveData<Boolean>()
    val showSnackBar: LiveData<Boolean>
        get() = _showSnackBar

    init {
        loadDataFromArgs(contentResultData)
        checkContentCategory()
    }

    private fun loadDataFromArgs(contentResultData: ContentResult) {
        _contents.value = contentResultData
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
        deleteById(_contents.value?.id as Int)
        _favoriteStatus.value = false
    }

    private fun checkContentCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val dataFromDatabase = favoriteRepository.getFavoriteById(_contents.value?.id as Int)
            if (dataFromDatabase?.id == _contents.value?.id) {
                withContext(Dispatchers.Main) { _favoriteStatus.value = dataFromDatabase?.isFavorite as Boolean }
            } else {
                withContext(Dispatchers.Main) { _favoriteStatus.value = false }
            }
        }
    }

    private fun addFavoriteContent(status: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            // Add data to favorite entity
            val addFavorite = FavoriteEntity().apply {
                isFavorite = status
                isMovie = contentIsMovie
                isTvShow = contentIsTvShow
                backdropPath = _contents.value?.backdropPath as String
                id = _contents.value?.id as Int
                title = _contents.value?.title as String
                posterPath = _contents.value?.posterPath as String
                voteAverage = _contents.value?.voteAverage as Double
                overview = _contents.value?.overview as String
                releaseDate = _contents.value?.releaseDate as String
            }

            insert(addFavorite)
        }
    }

    private fun insert(favorite: FavoriteEntity) = viewModelScope.launch(Dispatchers.IO) {
        favoriteRepository.insert(favorite)
    }

    private fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        favoriteRepository.deleteFavoriteById(id)
    }
}
