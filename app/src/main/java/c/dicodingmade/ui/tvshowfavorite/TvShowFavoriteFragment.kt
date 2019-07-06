package c.dicodingmade.ui.tvshowfavorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import c.dicodingmade.adapter.ContentAdapter
import c.dicodingmade.databinding.FragmentTvShowFavoriteBinding
import c.dicodingmade.ui.favorite.FavoriteFragmentDirections

class TvShowFavoriteFragment : Fragment() {
    private lateinit var tvShowFavoriteViewModel: TvShowFavoriteViewModel
    private lateinit var tvShowFavoriteViewModelFactory: TvShowFavoriteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTvShowFavoriteBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        tvShowFavoriteViewModelFactory = TvShowFavoriteViewModelFactory(application)
        tvShowFavoriteViewModel =
            ViewModelProviders.of(this, tvShowFavoriteViewModelFactory).get(TvShowFavoriteViewModel::class.java)
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
                        FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(
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
