package c.dicodingmade.di

import c.dicodingmade.domain.ContentResult
import c.dicodingmade.ui.detail.DetailViewModel
import c.dicodingmade.ui.movie.MovieViewModel
import c.dicodingmade.ui.moviefavorite.MovieFavoriteViewModel
import c.dicodingmade.ui.tvshow.TvShowViewModel
import c.dicodingmade.ui.tvshowfavorite.TvShowFavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class Module {
    val appModule = module {
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
    }
}