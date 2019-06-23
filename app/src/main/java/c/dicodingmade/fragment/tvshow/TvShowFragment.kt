package c.dicodingmade.fragment.tvshow


import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import c.dicodingmade.R
import c.dicodingmade.activity.detail.DetailActivity
import c.dicodingmade.model.MovieTvShowData
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : Fragment() {
    private var listTvShow: MutableList<MovieTvShowData> = mutableListOf()
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var tvShowPosterData: TypedArray
    private var tvShowTitleData: Array<String> = arrayOf()
    private var tvShowReleaseDateData: Array<String> = arrayOf()
    private var tvShowDescriptionData: Array<String> = arrayOf()
    private var tvShows: ArrayList<MovieTvShowData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvShowAdapter = TvShowAdapter(listTvShow) {
            val singleTvShowData = MovieTvShowData().apply {
                movieTvShowPoster = it.movieTvShowPoster
                movieTvShowTitle = it.movieTvShowTitle
                movieTvShowReleaseDate = it.movieTvShowReleaseDate
                movieTvShowDescription = it.movieTvShowDescription
            }
            val detailTvShowWithObjectIntent = Intent(context, DetailActivity::class.java)
                .putExtra(DetailActivity.EXTRA_DATA, singleTvShowData)
            startActivity(detailTvShowWithObjectIntent)
        }
        rv_tv_show.adapter = tvShowAdapter
        rv_tv_show.isNestedScrollingEnabled = false
        dataPreparation()
        addDataTvShow()
    }

    private fun addDataTvShow() {
        for (i in 0 until tvShowTitleData.size) {
            val tvShowData = MovieTvShowData()
            tvShowData.movieTvShowPoster = tvShowPosterData.getResourceId(i, -1)
            tvShowData.movieTvShowTitle = tvShowTitleData[i]
            tvShowData.movieTvShowReleaseDate = tvShowReleaseDateData[i]
            tvShowData.movieTvShowDescription = tvShowDescriptionData[i]
            tvShows.add(tvShowData)
        }

        listTvShow.clear()
        listTvShow.addAll(tvShows)
        tvShowAdapter.notifyDataSetChanged()
    }

    private fun dataPreparation() {
        tvShowPosterData = resources.obtainTypedArray(R.array.poster_tv_show_data)
        tvShowTitleData = resources.getStringArray(R.array.title_tv_show_data)
        tvShowReleaseDateData = resources.getStringArray(R.array.tv_show_release_date_data)
        tvShowDescriptionData = resources.getStringArray(R.array.tv_show_description_data)
    }
}
