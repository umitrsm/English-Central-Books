package com.egb.englishcentralbooks.ui.books.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egb.englishcentralbooks.R
import com.egb.englishcentralbooks.databinding.BookItemBinding
import com.egb.englishcentralbooks.db.entity.BookDb
import com.egb.englishcentralbooks.extensions.loadImageUrl


class BooksAdapter(
    val addAction: (book: BookDb) -> Unit,
    val deleteAction: (book: BookDb) -> Unit,
    val openDetail: (book: BookDb) -> Unit
) : ListAdapter<BookDb, BooksAdapter.BookItemViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookDb>() {
            override fun areItemsTheSame(oldItem: BookDb, newItem: BookDb): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: BookDb, newItem: BookDb): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun setItems(bookTypes: List<BookDb>?) {
        submitList(bookTypes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BookItemViewHolder(private val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookDb) {
            binding.bookName.text = item.author
            binding.bookImage.loadImageUrl(item.bookImage)
            if (item.fav) {
                binding.favImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        binding.favImage.context,
                        R.drawable.ic_added_fav_icon
                    )
                )
            } else {
                binding.favImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        binding.favImage.context,
                        R.drawable.ic_fav_icon
                    )
                )
            }
            binding.bookImage.setOnClickListener {
                openDetail.invoke(item)
            }
            binding.favImage.setOnClickListener {
                if (item.fav) {
                    item.fav = false
                    binding.favImage.setImageDrawable(
                        AppCompatResources.getDrawable(
                            binding.favImage.context,
                            R.drawable.ic_fav_icon
                        )
                    )
                    deleteAction.invoke(item)
                } else {
                    item.fav = true
                    binding.favImage.setImageDrawable(
                        AppCompatResources.getDrawable(
                            binding.favImage.context,
                            R.drawable.ic_added_fav_icon
                        )
                    )
                    addAction.invoke(item)
                }
            }
        }
    }

}