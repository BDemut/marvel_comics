package com.defconapplications.marvel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.defconapplications.marvel.databinding.RecyclerViewItemBinding
import com.defconapplications.marvel.repository.Comic

class ComicAdapter(val clickListener: ComicClickListener) : ListAdapter<Comic, ComicViewHolder>(
        ComicDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        return ComicViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.update(
            getItem(position),
            clickListener
        )
    }
}

class ComicViewHolder private constructor(val binding: RecyclerViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun update(comic: Comic, clickListener: ComicClickListener) {
        binding.name.text = comic.title.smartTruncate(50)
        binding.description.text = (comic.description ?: "-").smartTruncate(170)
        binding.writers.text = itemView.context.getString(R.string.written_by, comic.creators.getCreators(true))
        val imgUri = comic.thumbnailPath.toUri().buildUpon().scheme("https").build()
        Glide.with(binding.thumbnail.context)
                .load(imgUri)
                .into(binding.thumbnail)
        binding.frame.setOnClickListener {
            clickListener.onClick(comic)
        }
    }

    companion object {
        fun from(parent: ViewGroup) : ComicViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerViewItemBinding.inflate(inflater, parent, false)
            return ComicViewHolder(binding)
        }
    }
}

class ComicDiffCallback : DiffUtil.ItemCallback<Comic>() {
    override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.description == newItem.description
    }
}

class ComicClickListener(val clickListener: (comic: Comic) -> Unit) {
    fun onClick(comic: Comic) = clickListener(comic)
}