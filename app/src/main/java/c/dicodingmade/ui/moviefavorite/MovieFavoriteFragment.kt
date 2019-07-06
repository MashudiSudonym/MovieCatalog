package c.dicodingmade.ui.moviefavorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import c.dicodingmade.adapter.ContentAdapter
import c.dicodingmade.databinding.FragmentMovieFavoriteBinding
import c.dicodingmade.ui.favorite.FavoriteFragmentDirections

class MovieFavoriteFragment : Fragment() {
    private lateinit var movieFavoriteViewModel: MovieFavoriteViewModel
    private lateinit var movieFavoriteViewModelFactory: MovieFavoriteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieFavoriteBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        movieFavoriteViewModelFactory = MovieFavoriteViewModelFactory(application)
        movieFavoriteViewModel =
            ViewModelProviders.of(this, movieFavoriteViewModelFactory).get(MovieFavoriteViewModel::class.java)
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
