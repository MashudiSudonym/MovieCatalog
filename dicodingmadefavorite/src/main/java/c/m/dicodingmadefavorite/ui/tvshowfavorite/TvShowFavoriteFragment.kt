package c.m.dicodingmadefavorite.ui.tvshowfavorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import c.m.dicodingmadefavorite.adapter.ContentAdapter
import c.m.dicodingmadefavorite.databinding.FragmentTvShowFavoriteBinding
import c.m.dicodingmadefavorite.ui.main.MainFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel


class TvShowFavoriteFragment : Fragment() {
    private val tvShowFavoriteViewModel: TvShowFavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTvShowFavoriteBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.tvShowFavoriteViewModel = tvShowFavoriteViewModel
        binding.rvTvShowFavorite.adapter = ContentAdapter(ContentAdapter.OnClickListener {
            tvShowFavoriteViewModel.displayDetail(it)
        })

        tvShowFavoriteViewModel.tvShowFavoriteList.observe(this, Observer {
            tvShowFavoriteViewModel.tvShowFavoriteData(it)
        })
        tvShowFavoriteViewModel.navigateToDetail.observe(this, Observer {
            if (it != null) {
                this.findNavController()
                    .navigate(
                        MainFragmentDirections.actionMainFragmentToDetailFragment(
                            it.title,
                            it,
                            isMovie = false,
                            isTvShow = true
                        )
                    )
                tvShowFavoriteViewModel.displayDetailComplete()
            }
        })
        return binding.root
    }

}
