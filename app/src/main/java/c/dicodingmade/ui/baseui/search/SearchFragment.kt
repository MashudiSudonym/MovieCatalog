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
import c.dicodingmade.ui.movieui.moviesearch.MovieSearchFragment
import c.dicodingmade.ui.tvshowui.tvshowsearch.TvShowSearchFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    private lateinit var tabViewPagerAdapter: TabViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
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
