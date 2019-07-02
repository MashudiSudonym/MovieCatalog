package c.dicodingmade.ui.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import c.dicodingmade.R
import c.dicodingmade.ui.moviefavorite.MovieFavoriteFragment
import c.dicodingmade.ui.tvshowfavorite.TvShowFavoriteFragment
import c.dicodingmade.util.TabViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {
    private lateinit var tabViewPagerAdapter: TabViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabViewPagerAdapter = TabViewPagerAdapter(childFragmentManager).apply {
            addFragment(MovieFavoriteFragment(), resources.getString(R.string.title_tab_movie))
            addFragment(TvShowFavoriteFragment(), resources.getString(R.string.title_tab_tv_show))
        }
        view_pager_favorite.adapter = tabViewPagerAdapter
    }

}
