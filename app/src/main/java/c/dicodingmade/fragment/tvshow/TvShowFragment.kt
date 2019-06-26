package c.dicodingmade.fragment.tvshow


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import c.dicodingmade.databinding.FragmentTvShowBinding

class TvShowFragment : Fragment() {

    private val tvShowViewModel: TvShowViewModel by lazy {
        ViewModelProviders.of(this).get(TvShowViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTvShowBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.tvShowViewModel = tvShowViewModel

        binding.rvTvShow.adapter = TvShowAdapter(TvShowAdapter.OnClickListener {
            Toast.makeText(context, it.originalName, Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }
}
