package c.dicodingmade.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import c.dicodingmade.R
import c.dicodingmade.ui.movie.MovieFragment
import c.dicodingmade.ui.tvshow.TvShowFragment
import c.dicodingmade.util.TabViewPagerAdapter
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
            addFragment(MovieFragment(), resources.getString(R.string.title_tab_movie))
            addFragment(TvShowFragment(), resources.getString(R.string.title_tab_tv_show))
        }
        view_pager_main.adapter = tabViewPagerAdapter
    }
}
