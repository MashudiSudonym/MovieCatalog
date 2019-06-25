package c.dicodingmade.fragment.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import c.dicodingmade.R
import c.dicodingmade.model.MovieTvShowData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_movie_tv_show.*

class MovieAdapter(
    private val movieTvShowData: List<MovieTvShowData>,
    private val listener: (MovieTvShowData) -> Unit
) :
    RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_movie_tv_show, parent, false)
    )

    override fun getItemCount(): Int = movieTvShowData.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bindItem(movieTvShowData[position], listener)
}

class MovieViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bindItem(movieTvShowData: MovieTvShowData, listener: (MovieTvShowData) -> Unit) {
        layout_item_movie_tv_show.setOnClickListener { listener(movieTvShowData) }

        Glide.with(itemView.context)
            .load(movieTvShowData.movieTvShowPoster)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_error)
            )
            .into(img_poster)

        tv_title.text = movieTvShowData.movieTvShowTitle
        tv_release_date.text = movieTvShowData.movieTvShowReleaseDate
        tv_description.text = movieTvShowData.movieTvShowDescription
    }
}
