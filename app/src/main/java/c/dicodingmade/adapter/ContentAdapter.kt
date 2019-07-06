package c.dicodingmade.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import c.dicodingmade.databinding.ItemListContentBinding
import c.dicodingmade.domain.ContentResult

class ContentAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ContentResult, ContentAdapter.MovieViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            ItemListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val contents = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(contents) }
        holder.bind(contents)
    }

    class OnClickListener(val clickListener: (contentResult: ContentResult) -> Unit) {
        fun onClick(contentResult: ContentResult) = clickListener(contentResult)
    }

    class MovieViewHolder(private var binding: ItemListContentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contentResult: ContentResult) {
            binding.content = contentResult
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ContentResult>() {
        override fun areItemsTheSame(oldItem: ContentResult, newItem: ContentResult): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: ContentResult, newItem: ContentResult): Boolean =
            oldItem.id == newItem.id
    }
}
