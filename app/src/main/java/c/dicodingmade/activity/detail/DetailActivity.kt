package c.dicodingmade.activity.detail

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import c.dicodingmade.R
import c.dicodingmade.model.MovieTvShowData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dataMovieTvShow = intent.getParcelableExtra<MovieTvShowData>(EXTRA_DATA)

        setSupportActionBar(toolbar_detail)
        supportActionBar?.apply {
            title = dataMovieTvShow.movieTvShowTitle
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        collapsing_toolbar_detail.apply {
            setExpandedTitleColor(Color.WHITE)
        }

        Glide.with(this)
            .load(dataMovieTvShow.movieTvShowPoster)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_error)
            )
            .into(img_poster_toolbar)

        Glide.with(this)
            .load(dataMovieTvShow.movieTvShowPoster)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_error)
            )
            .into(img_detail_poster)

        tv_detail_title.text = dataMovieTvShow.movieTvShowTitle
        tv_detail_movie_release_date.text = dataMovieTvShow.movieTvShowReleaseDate
        tv_detail_movie_description.text = dataMovieTvShow.movieTvShowDescription
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
