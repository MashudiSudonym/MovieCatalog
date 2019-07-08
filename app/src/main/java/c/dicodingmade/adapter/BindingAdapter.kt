package c.dicodingmade.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import c.dicodingmade.R
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.network.Services
import c.dicodingmade.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

// Recycler View Content
@BindingAdapter("listDataContent")
fun bindMovieRecyclerView(recyclerView: RecyclerView, data: List<ContentResult>?) {
    val adapter = recyclerView.adapter as ContentAdapter
    adapter.submitList(data)
    adapter.notifyDataSetChanged()
}

// Poster
@BindingAdapter("imageUrl")
fun bindPoster(imgView: ImageView, imgPath: String?) {
    Glide.with(imgView.context)
        .load(Services.BASE_URL_POSTER + imgPath)
        .apply(
            RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
        .into(imgView)
}

// Progress Bar for waiting loading data
@BindingAdapter("apiStatusProgressBar")
fun bindApiStatusImageErrorConnection(progressBarStatus: ProgressBar, viewStatusConnection: ViewStatusConnection?) {
    when (viewStatusConnection) {
        ViewStatusConnection.LOADING -> progressBarStatus.visible()
        ViewStatusConnection.ERROR -> progressBarStatus.gone()
        ViewStatusConnection.DONE -> progressBarStatus.gone()
    }
}

// Error no connection network icon
@BindingAdapter("apiStatusImageErrorConnection")
fun bindApiStatusImageErrorConnection(imgStatus: ImageView, viewStatusConnection: ViewStatusConnection?) {
    when (viewStatusConnection) {
        ViewStatusConnection.LOADING -> imgStatus.gone()
        ViewStatusConnection.ERROR -> {
            imgStatus.visible()
            imgStatus.setImageResource(R.drawable.ic_connection_error)
        }
        ViewStatusConnection.DONE -> imgStatus.gone()
    }
}

// Error no connection network icon
@BindingAdapter("statusImageFavoritePage")
fun bindStatusImageFavoritePage(imgStatus: ImageView, viewStatusConnection: ViewStatusConnection?) {
    when (viewStatusConnection) {
        ViewStatusConnection.LOADING -> imgStatus.gone()
        ViewStatusConnection.ERROR -> {
            imgStatus.visible()
            imgStatus.setImageResource(R.drawable.ic_favorite_true)
        }
        ViewStatusConnection.DONE -> imgStatus.gone()
    }
}

// If error connection recycler view must be hiding
@BindingAdapter("apiStatusRecyclerViewErrorConnection")
fun bindApiStatusRecyclerViewErrorConnection(recyclerView: RecyclerView, viewStatusConnection: ViewStatusConnection?) {
    when (viewStatusConnection) {
        ViewStatusConnection.LOADING -> recyclerView.invisible()
        ViewStatusConnection.ERROR -> recyclerView.invisible()
        ViewStatusConnection.DONE -> recyclerView.visible()
    }
}

// Converter Text format for release date movie / tv show
@SuppressLint("SetTextI18n")
@BindingAdapter("releaseDate")
fun bindReleaseDate(textView: TextView, textPath: String?) {
    textView.text =
        textView.resources.getString(R.string.movie_tv_show_release_date) + simpleDateFormat(
            textPath as String
        )
}

// Converter Text format for rating movie / tv show
@BindingAdapter("voteAverage")
fun bindVoteAverage(textView: TextView, textPath: Double?) {
    textView.text = textPath.toString()
}

@BindingAdapter("favoriteStatus")
fun bindFavoriteStatus(floatingActionButton: FloatingActionButton, status: Boolean) {
    when (status) {
        true -> {
            floatingActionButton.setColorFilter(Color.argb(255, 236, 64, 122))
        }
        false -> {
            floatingActionButton.setColorFilter(Color.DKGRAY)
        }
    }
}