package c.dicodingmade.ui.movie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import c.dicodingmade.adapter.ContentAdapter
import c.dicodingmade.databinding.FragmentMovieBinding
import c.dicodingmade.ui.main.MainFragmentDirections

class MovieFragment : Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieViewModelFactory: MovieViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        movieViewModelFactory = MovieViewModelFactory(application)
        movieViewModel = ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel::class.java)
        binding.lifecycleOwner = this
        binding.movieViewModel = movieViewModel
        binding.rvMovie.adapter = ContentAdapter(ContentAdapter.OnClickListener {
                movieViewModel.displayDetail(it)
            })

        movieViewModel.contentMovie.observe(this, Observer {
            movieViewModel.contentMovieData(it)
        })
        movieViewModel.navigateToDetail.observe(this, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(
                        MainFragmentDirections.actionMainFragmentToDetailFragment(
                            it.title,
                            it,
                            isMovie = true,
                            isTvShow = false
                        )
                    )
                movieViewModel.displayDetailComplete()
            }
        })

        return binding.root
    }
}
