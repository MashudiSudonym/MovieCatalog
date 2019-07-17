package c.dicodingmade.ui.movieui.moviefavorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import c.dicodingmade.adapter.ContentAdapter
import c.dicodingmade.databinding.FragmentMovieFavoriteBinding
import c.dicodingmade.ui.baseui.favorite.FavoriteFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFavoriteFragment : Fragment() {
    private val movieFavoriteViewModel: MovieFavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieFavoriteBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.movieFavoriteViewModel = movieFavoriteViewModel
        binding.rvMovieFavorite.adapter = ContentAdapter(ContentAdapter.OnClickListener {
            movieFavoriteViewModel.displayDetail(it)
        })

        movieFavoriteViewModel.movieFavoriteList.observe(this, Observer {
            movieFavoriteViewModel.movieFavoriteData(it)
        })
        movieFavoriteViewModel.navigateToDetail.observe(this, Observer {
            if (it != null) {
                this.findNavController()
                    .navigate(
                        FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(
                            it.title,
                            it,
                            isMovie = true,
                            isTvShow = false
                        )
                    )
                movieFavoriteViewModel.displayDetailComplete()
            }
        })

        return binding.root
    }

}
