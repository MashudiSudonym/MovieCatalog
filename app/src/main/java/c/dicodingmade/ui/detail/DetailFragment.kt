package c.dicodingmade.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import c.dicodingmade.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailViewModelFactory: DetailViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        val movieTvShowData by navArgs<DetailFragmentArgs>()
        val application = requireNotNull(this.activity).application

        detailViewModelFactory =
            DetailViewModelFactory(
                movieTvShowData.movieResultData,
                movieTvShowData.tvShowResultData,
                application
            )
        detailViewModel = ViewModelProviders.of(this, detailViewModelFactory).get(DetailViewModel::class.java)
        binding.lifecycleOwner = this
        binding.detailViewModel = detailViewModel

        detailViewModel.favoriteStatus.observe(this, Observer {
            if (it == true) {
                snackbar(view as View, it.toString())
            } else {
                snackbar(view as View, it.toString())
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsing_toolbar_detail.apply {
            setExpandedTitleColor(Color.WHITE)
            setupWithNavController(toolbar_detail, findNavController())
        }
    }

    private fun snackbar(view: View, value: String) = Snackbar.make(view, value, Snackbar.LENGTH_SHORT).show()

}
