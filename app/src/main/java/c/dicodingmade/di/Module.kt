package c.dicodingmade.di

import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.repository.*
import c.dicodingmade.ui.baseui.search.SearchViewModel
import c.dicodingmade.ui.detail.DetailViewModel
import c.dicodingmade.ui.movieui.movie.MovieViewModel
import c.dicodingmade.ui.movieui.moviefavorite.MovieFavoriteViewModel
import c.dicodingmade.ui.movieui.moviesearch.MovieSearchViewModel
import c.dicodingmade.ui.tvshowui.tvshow.TvShowViewModel
import c.dicodingmade.ui.tvshowui.tvshowfavorite.TvShowFavoriteViewModel
import c.dicodingmade.ui.tvshowui.tvshowsearch.TvShowSearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class Module {
    val viewModelModule = module {
        viewModel { MovieViewModel(get()) }
        viewModel { TvShowViewModel(get()) }
        viewModel { MovieFavoriteViewModel(get()) }
        viewModel { TvShowFavoriteViewModel(get()) }
        viewModel { (contentData: ContentResult, isMovie: Boolean, isTvShow: Boolean) ->
            DetailViewModel(
                contentData,
                get(),
                isMovie,
                isTvShow
            )
        }
        viewModel { SearchViewModel(get()) }
        viewModel { MovieSearchViewModel(get()) }
        viewModel { TvShowSearchViewModel(get()) }
    }

    val repositoryModule = module {
        single { ContentMovieRepository(ApplicationDatabase.getDatabase(androidApplication())) }
        single { ContentMovieSearchRepository(ApplicationDatabase.getDatabase(androidApplication())) }
        single { ContentMovieUpcomingRepository(ApplicationDatabase.getDatabase(androidApplication())) }
        single { ContentTvShowRepository(ApplicationDatabase.getDatabase(androidApplication())) }
        single { ContentTvShowSearchRepository(ApplicationDatabase.getDatabase(androidApplication())) }
        single { FavoriteRepository(ApplicationDatabase.getDatabase(androidApplication())) }
    }
}