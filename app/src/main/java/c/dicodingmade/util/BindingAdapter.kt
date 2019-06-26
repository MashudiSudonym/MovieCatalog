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
import c.dicodingmade.model.ApiStatus
import c.dicodingmade.model.MovieResult
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("listDataMovie")
fun bindMovieRecyclerView(recyclerView: RecyclerView, data: List<MovieResult>?) {
    val adapter = recyclerView.adapter as MovieAdapter
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

@BindingAdapter("movieApiStatusProgressBar")
fun bindMovieApiStatusImageErrorConnection(progressBarStatus: ProgressBar, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> progressBarStatus.visible()
        ApiStatus.ERROR -> progressBarStatus.gone()
        ApiStatus.DONE -> progressBarStatus.gone()
    }
}

@BindingAdapter("movieApiStatusImageErrorConnection")
fun bindMovieApiStatusImageErrorConnection(imgStatus: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> imgStatus.gone()
        ApiStatus.ERROR -> {
            imgStatus.visible()
            imgStatus.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> imgStatus.gone()
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("releaseDate")
fun bindReleaseDate(textView: TextView, textPath: String?) {
    textView.text =
        textView.resources.getString(R.string.movie_tv_show_release_date) + simpleDateFormat(textPath as String)
}