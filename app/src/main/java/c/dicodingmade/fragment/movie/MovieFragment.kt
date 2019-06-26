package c.dicodingmade.fragment.movie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import c.dicodingmade.databinding.FragmentMovieBinding

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
            Toast.makeText(context, it.originalTitle, Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }
}
