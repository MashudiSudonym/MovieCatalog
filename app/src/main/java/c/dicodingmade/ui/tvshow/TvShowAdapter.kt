package c.dicodingmade.ui.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import c.dicodingmade.databinding.ItemListTvShowBinding
import c.dicodingmade.model.TvShowResult

class TvShowAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<TvShowResult, TvShowAdapter.TvShowViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder = TvShowViewHolder(
        ItemListTvShowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShows = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(tvShows) }
        holder.bind(tvShows)
    }

    class OnClickListener(val clickListener: (tvShowResult: TvShowResult) -> Unit) {
        fun onClick(tvShowResult: TvShowResult) = clickListener(tvShowResult)
    }

    class TvShowViewHolder(private var binding: ItemListTvShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowResult: TvShowResult) {
            binding.tvShow = tvShowResult
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TvShowResult>() {
        override fun areItemsTheSame(oldItem: TvShowResult, newItem: TvShowResult): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: TvShowResult, newItem: TvShowResult): Boolean =
            oldItem.id == newItem.id
    }
}
