package c.dicodingmade.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import c.dicodingmade.BuildConfig
import c.dicodingmade.R
import c.dicodingmade.fragment.movie.MovieAdapter
import c.dicodingmade.fragment.tvshow.TvShowAdapter
import c.dicodingmade.model.MovieResult
import c.dicodingmade.model.TvShowResult
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("listDataMovie")
fun bindMovieRecyclerView(recyclerView: RecyclerView, data: List<MovieResult>?) {
    val adapter = recyclerView.adapter as MovieAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataTvShow")
fun bindTvShowRecyclerView(recyclerView: RecyclerView, data: List<TvShowResult>?) {
    val adapter = recyclerView.adapter as TvShowAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindPoster(imgView: ImageView, imgPath: String?) {
    Glide.with(imgView.context)
        .load(BuildConfig.BASE_URL_POSTER + imgPath)
        .apply(
            RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
        .into(imgView)
}

@BindingAdapter("apiStatusProgressBar")
fun bindApiStatusImageErrorConnection(progressBarStatus: ProgressBar, status: ApiStatusConnection?) {
    when (status) {
        ApiStatusConnection.LOADING -> progressBarStatus.visible()
        ApiStatusConnection.ERROR -> progressBarStatus.gone()
        ApiStatusConnection.DONE -> progressBarStatus.gone()
    }
}

@BindingAdapter("apiStatusImageErrorConnection")
fun bindApiStatusImageErrorConnection(imgStatus: ImageView, status: ApiStatusConnection?) {
    when (status) {
        ApiStatusConnection.LOADING -> imgStatus.gone()
        ApiStatusConnection.ERROR -> {
            imgStatus.visible()
            imgStatus.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatusConnection.DONE -> imgStatus.gone()
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("releaseDate")
fun bindReleaseDate(textView: TextView, textPath: String?) {
    textView.text =
        textView.resources.getString(R.string.movie_tv_show_release_date) + simpleDateFormat(textPath as String)
}

@BindingAdapter("voteAverage")
fun bindVoteAverage(textView: TextView, textPath: Double?) {
    textView.text = textPath.toString()
}