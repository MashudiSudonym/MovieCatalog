package c.dicodingmade.fragment.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import c.dicodingmade.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        binding.lifecycleOwner = this
        binding.detailViewModel = detailViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsing_toolbar_detail.apply {
            setExpandedTitleColor(Color.WHITE)
            setupWithNavController(toolbar_detail, findNavController())
        }
    }

}
