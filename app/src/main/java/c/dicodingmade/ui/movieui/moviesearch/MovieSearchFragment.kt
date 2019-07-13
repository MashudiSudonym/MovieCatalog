package c.dicodingmade.ui.movieui.moviesearch


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import c.dicodingmade.adapter.ContentAdapter
import c.dicodingmade.databinding.FragmentMovieSearchBinding
import c.dicodingmade.ui.baseui.search.SearchFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieSearchFragment : Fragment() {
    private val movieSearchViewModel: MovieSearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieSearchBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.movieSearchViewModel = movieSearchViewModel
        binding.rvMovieSearch.adapter = ContentAdapter(ContentAdapter.OnClickListener {
            movieSearchViewModel.displayDetail(it)
        })

        movieSearchViewModel.contentMovie.observe(this, Observer {
            movieSearchViewModel.contentMovieData(it)
        })
        movieSearchViewModel.navigateToDetail.observe(this, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(
                        SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                            it.title,
                            it,
                            isMovie = true,
                            isTvShow = false
                        )
                    )
                movieSearchViewModel.displayDetailComplete()
            }
        })

        return binding.root
    }


}
