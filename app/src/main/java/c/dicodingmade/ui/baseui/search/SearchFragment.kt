package c.dicodingmade.ui.baseui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import c.dicodingmade.R
import c.dicodingmade.adapter.TabViewPagerAdapter
import c.dicodingmade.databinding.FragmentSearchBinding
import c.dicodingmade.ui.movieui.moviesearch.MovieSearchFragment
import c.dicodingmade.ui.tvshowui.tvshowsearch.TvShowSearchFragment
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private lateinit var tabViewPagerAdapter: TabViewPagerAdapter
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.searchViewModel = searchViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_search.setupWithNavController(findNavController())

        tabViewPagerAdapter = TabViewPagerAdapter(childFragmentManager).apply {
            addFragment(MovieSearchFragment(), resources.getString(R.string.title_tab_movie))
            addFragment(TvShowSearchFragment(), resources.getString(R.string.title_tab_tv_show))
        }
        view_pager_search.adapter = tabViewPagerAdapter
    }

}
