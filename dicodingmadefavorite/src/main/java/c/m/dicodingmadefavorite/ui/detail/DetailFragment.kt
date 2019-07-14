package c.m.dicodingmadefavorite.ui.detail


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import c.m.dicodingmadefavorite.R
import c.m.dicodingmadefavorite.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {
    private val movieTvShowData by navArgs<DetailFragmentArgs>()
    private val detailViewModel: DetailViewModel by viewModel {
        parametersOf(movieTvShowData.contentData, movieTvShowData.isMovie, movieTvShowData.isTvShow)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.detailViewModel = detailViewModel

        detailViewModel.showSnackBar.observe(this, Observer {
            when (it) {
                true -> snackBar(view as View, resources.getString(R.string.add_to_favorite))
                false -> snackBar(view as View, resources.getString(R.string.remove_from_favorite))
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

    private fun snackBar(view: View, value: String) = Snackbar.make(view, value, Snackbar.LENGTH_SHORT).show()

}
