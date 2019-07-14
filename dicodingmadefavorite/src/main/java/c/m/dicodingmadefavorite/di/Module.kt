package c.m.dicodingmadefavorite.di

import c.m.dicodingmadefavorite.domain.ContentResult
import c.m.dicodingmadefavorite.ui.detail.DetailViewModel
import c.m.dicodingmadefavorite.ui.moviefavorite.MovieFavoriteViewModel
import c.m.dicodingmadefavorite.ui.tvshowfavorite.TvShowFavoriteViewModel
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