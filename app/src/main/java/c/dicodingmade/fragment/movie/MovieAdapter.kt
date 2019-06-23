package c.dicodingmade.fragment.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import c.dicodingmade.R
import c.dicodingmade.model.MovieData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_movie_tv_show.*

class MovieAdapter(
    private val movieData: List<MovieData>,
    private val listener: (MovieData) -> Unit
) :
    RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_movie_tv_show, parent, false)
    )

    override fun getItemCount(): Int = movieData.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bindItem(movieData[position], listener)
}

class MovieViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bindItem(movieData: MovieData, listener: (MovieData) -> Unit) {
        layout_item_movie_tv_show.setOnClickListener { listener(movieData) }

        Glide.with(img_poster.context)
            .load(movieData.moviePoster)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_error)
            )
            .into(img_poster)

        tv_title.text = movieData.movieTitle
        tv_release_date.text = movieData.movieReleaseDate
        tv_description.text = movieData.movieDescription
    }
}
