package c.dicodingmade.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import c.dicodingmade.R
import c.dicodingmade.fragment.movie.MovieFragment
import c.dicodingmade.fragment.tvshow.TvShowFragment
import c.dicodingmade.util.TabViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var tabViewPagerAdapter: TabViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar_main)
        supportActionBar.apply {
            title = resources.getString(R.string.app_name)
        }

        tabViewPagerAdapter = TabViewPagerAdapter(supportFragmentManager).apply {
            addFragment(MovieFragment(), resources.getString(R.string.title_tab_movie))
            addFragment(TvShowFragment(), resources.getString(R.string.title_tab_tv_show))
        }
        view_pager_main.adapter = tabViewPagerAdapter
        layout_tab_main.setupWithViewPager(view_pager_main)
    }
}
