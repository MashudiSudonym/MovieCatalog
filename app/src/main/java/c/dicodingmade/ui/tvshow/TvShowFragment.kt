package c.dicodingmade.ui.tvshow


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import c.dicodingmade.adapter.ContentAdapter
import c.dicodingmade.databinding.FragmentTvShowBinding
import c.dicodingmade.ui.main.MainFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {
    private val tvShowViewModel: TvShowViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTvShowBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.tvShowViewModel = tvShowViewModel
        binding.rvTvShow.adapter = ContentAdapter(ContentAdapter.OnClickListener {
            tvShowViewModel.displayDetail(it)
        })

        tvShowViewModel.contentTvShow.observe(this, Observer {
            tvShowViewModel.contentTvShowData(it)
        })
        tvShowViewModel.navigateToDetail.observe(this, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(
                        MainFragmentDirections.actionMainFragmentToDetailFragment(
                            it.title,
                            it,
                            isMovie = false,
                            isTvShow = true
                        )
                    )
                tvShowViewModel.displayDetailComplete()
            }
        })

        return binding.root
    }
}
