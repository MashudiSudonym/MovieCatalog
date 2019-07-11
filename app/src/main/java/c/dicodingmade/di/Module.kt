package c.dicodingmade.di

import c.dicodingmade.domain.ContentResult
import c.dicodingmade.ui.detail.DetailViewModel
import c.dicodingmade.ui.movieui.movie.MovieViewModel
import c.dicodingmade.ui.movieui.moviefavorite.MovieFavoriteViewModel
import c.dicodingmade.ui.setting.SettingsViewModel
import c.dicodingmade.ui.tvshowui.tvshow.TvShowViewModel
import c.dicodingmade.ui.tvshowui.tvshowfavorite.TvShowFavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class Module {
    /*
    * Note:
    * Need more learning about dependency injection using KOIN
    * while currently used to overcome "View Model and View Model Factory"
    * check the branch "submission-4" which still doesn't use KOIN to see the difference
    * */
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
        viewModel { SettingsViewModel(get()) }
    }
}