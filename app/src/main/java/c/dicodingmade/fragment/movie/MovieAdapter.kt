package c.dicodingmade.fragment.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import c.dicodingmade.databinding.ItemListMovieBinding
import c.dicodingmade.model.MovieResult

class MovieAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<MovieResult, MovieAdapter.MovieViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(
        ItemListMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(movies) }
        holder.bind(movies)
    }

    class OnClickListener(val clickListener: (movieResult: MovieResult) -> Unit) {
        fun onClick(movieResult: MovieResult) = clickListener(movieResult)
    }

    class MovieViewHolder(private var binding: ItemListMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieResult: MovieResult) {
            binding.movie = movieResult
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean = oldItem.id == newItem.id
    }
}
