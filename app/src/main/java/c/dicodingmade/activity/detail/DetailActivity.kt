package c.dicodingmade.activity.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import c.dicodingmade.R
import c.dicodingmade.model.MovieData
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

        val dataMovieTvShow = intent.getParcelableExtra<MovieData>(EXTRA_DATA)

        setSupportActionBar(toolbar_detail)
        supportActionBar?.apply {
            title = dataMovieTvShow.movieTitle
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        Glide.with(img_detail_poster.context)
            .load(dataMovieTvShow.moviePoster)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_error)
            )
            .into(img_detail_poster)

        tv_detail_title.text = dataMovieTvShow.movieTitle
        tv_detail_movie_release_date.text = dataMovieTvShow.movieReleaseDate
        tv_detail_movie_description.text = dataMovieTvShow.movieDescription
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
