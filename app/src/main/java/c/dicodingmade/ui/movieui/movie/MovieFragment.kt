package c.dicodingmade.ui.movieui.movie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import c.dicodingmade.adapter.ContentAdapter
import c.dicodingmade.databinding.FragmentMovieBinding
import c.dicodingmade.ui.baseui.main.MainFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {
    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieBinding.inflate(inflater)

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
