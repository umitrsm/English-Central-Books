package com.egb.englishcentralbooks.ui.books.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egb.englishcentralbooks.databinding.ItemTextBinding
import com.egb.englishcentralbooks.db.entity.BookListDb
import com.egb.englishcentralbooks.ui.books.BookTypeInterface


class SelectionBottomSheetAdapter(
    private val listener: BookTypeInterface,val closeAction: () -> Unit
) : ListAdapter<BookListDb, SelectionBottomSheetAdapter.TextListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookListDb>() {
            override fun areItemsTheSame(oldItem: BookListDb, newItem: BookListDb): Boolean {
                return oldItem.list_name_encoded == newItem.list_name_encoded
            }

            override fun areContentsTheSame(oldItem: BookListDb, newItem: BookListDb): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun setItems(bookTypes: List<BookListDb>?) {
        submitList(bookTypes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextListViewHolder {
        val binding = ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TextListViewHolder(private val binding: ItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookListDb) {
            binding.itemTextTextView.text = item.display_name
            binding.root.setOnClickListener {
                listener.onRowClick(item)
                closeAction.invoke()
            }
        }
    }

}