package c.dicodingmade.ui.tvshowui.tvshowsearch


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import c.dicodingmade.adapter.ContentAdapter
import c.dicodingmade.databinding.FragmentTvShowSearchBinding
import c.dicodingmade.ui.baseui.search.SearchFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowSearchFragment : Fragment() {
    private val tvShowSearchViewModel: TvShowSearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTvShowSearchBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.tvShowSearchViewModel = tvShowSearchViewModel

        binding.rvTvShowSearch.adapter = ContentAdapter(ContentAdapter.OnClickListener {
            tvShowSearchViewModel.displayDetail(it)
        })

        tvShowSearchViewModel.contentTvShow.observe(this, Observer {
            tvShowSearchViewModel.contentTvShowData(it)
        })
        tvShowSearchViewModel.navigateToDetail.observe(this, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(
                        SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                            it.title,
                            it,
                            isMovie = false,
                            isTvShow = true
                        )
                    )
                tvShowSearchViewModel.displayDetailComplete()
            }
        })

        return binding.root
    }


}
