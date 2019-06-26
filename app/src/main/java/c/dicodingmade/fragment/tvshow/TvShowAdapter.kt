package c.dicodingmade.fragment.tvshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import c.dicodingmade.BuildConfig
import c.dicodingmade.R
import c.dicodingmade.model.TvShowResult
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_movie_tv_show.*

class TvShowAdapter(
    private val tvShowResult: List<TvShowResult>,
    private val listener: (TvShowResult) -> Unit
) : RecyclerView.Adapter<TvShowViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder = TvShowViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_movie_tv_show, parent, false)
    )

    override fun getItemCount(): Int = tvShowResult.size

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) =
        holder.bindItem(tvShowResult[position], listener)
}

class TvShowViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bindItem(tvShowResult: TvShowResult, listener: (TvShowResult) -> Unit) {
        layout_item_movie_tv_show.setOnClickListener { listener(tvShowResult) }

        Glide.with(img_poster.context)
            .load("${BuildConfig.BASE_URL_API}original/${tvShowResult.backdropPath}")
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_error)
            )
            .into(img_poster)

        tv_title.text = tvShowResult.name
        tv_release_date.text = tvShowResult.firstAirDate
        tv_description.text = tvShowResult.overview
    }
}
