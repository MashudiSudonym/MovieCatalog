package c.m.dicodingmadefavorite.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import c.m.dicodingmadefavorite.R
import c.m.dicodingmadefavorite.adapter.TabViewPagerAdapter
import c.m.dicodingmadefavorite.ui.moviefavorite.MovieFavoriteFragment
import c.m.dicodingmadefavorite.ui.tvshowfavorite.TvShowFavoriteFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private lateinit var tabViewPagerAdapter: TabViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabViewPagerAdapter = TabViewPagerAdapter(childFragmentManager).apply {
            addFragment(MovieFavoriteFragment(), resources.getString(R.string.title_tab_movie))
            addFragment(TvShowFavoriteFragment(), resources.getString(R.string.title_tab_tv_show))
        }
        view_pager_main.adapter = tabViewPagerAdapter
    }

}
