package c.dicodingmade.fragment.tvshow


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import c.dicodingmade.databinding.FragmentTvShowBinding
import c.dicodingmade.fragment.main.MainFragmentDirections

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
            tvShowViewModel.displayDetail(it)
        })
        tvShowViewModel.navigateToDetail.observe(this, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(it.id, it.name))
                tvShowViewModel.displaDetailComplete()
            }
        })

        return binding.root
    }
}
