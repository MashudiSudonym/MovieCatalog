package c.dicodingmade.fragment.movie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import c.dicodingmade.databinding.FragmentMovieBinding
import c.dicodingmade.fragment.main.MainFragmentDirections

class MovieFragment : Fragment() {
    private val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this).get(MovieViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.movieViewModel = movieViewModel
        binding.rvMovie.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            movieViewModel.displayDetail(it)
        })
        movieViewModel.navigateToDetail.observe(this, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(it.id, it.title))
                movieViewModel.displayDetailComplete()
            }
        })

        return binding.root
    }
}
