package c.dicodingmade.fragment.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import c.dicodingmade.BuildConfig
import c.dicodingmade.R
import c.dicodingmade.model.MovieResult
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_movie_tv_show.*

class MovieAdapter(
    private val movieResult: List<MovieResult>,
    private val listener: (MovieResult) -> Unit
) :
    RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_movie_tv_show, parent, false)
    )

    override fun getItemCount(): Int = movieResult.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bindItem(movieResult[position], listener)
}

class MovieViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bindItem(movieResult: MovieResult, listener: (MovieResult) -> Unit) {
        layout_item_movie_tv_show.setOnClickListener { listener(movieResult) }

        Glide.with(itemView.context)
            .load("${BuildConfig.BASE_URL_API}original/${movieResult.backdropPath}")
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_error)
            )
            .into(img_poster)

        tv_title.text = movieResult.originalTitle
        tv_release_date.text = movieResult.releaseDate
        tv_description.text = movieResult.overview
    }
}
