package c.dicodingmade.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import c.dicodingmade.BuildConfig
import c.dicodingmade.R
import c.dicodingmade.fragment.movie.MovieAdapter
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
                .error(R.drawable.ic_error)
        )
        .into(imgView)
}