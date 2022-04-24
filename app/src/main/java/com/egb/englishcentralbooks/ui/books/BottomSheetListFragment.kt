package com.egb.englishcentralbooks.ui.books

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egb.englishcentralbooks.R
import com.egb.englishcentralbooks.databinding.RoundedBottomSheetFragmentListBinding
import com.egb.englishcentralbooks.db.entity.BookListDb
import com.egb.englishcentralbooks.ui.base.RoundedBottomSheetDialogFragment
import com.egb.englishcentralbooks.ui.books.adapter.SelectionBottomSheetAdapter
import com.egb.englishcentralbooks.utils.viewBinding

internal class BottomSheetListFragment : RoundedBottomSheetDialogFragment(R.layout.rounded_bottom_sheet_fragment_list) {

    companion object {
        fun newInstance(list: List<BookListDb>, bookTypeInterface: BookTypeInterface) =
            BottomSheetListFragment().apply {
                this.bookList = list
                this.bookTypeInterface = bookTypeInterface
            }
    }

    private lateinit var bookList: List<BookListDb>
    private lateinit var bookTypeInterface: BookTypeInterface


    private val textListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SelectionBottomSheetAdapter(bookTypeInterface, closeAction = {
            dismiss()
        })
    }

    private val binding by viewBinding(RoundedBottomSheetFragmentListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = textListAdapter
            textListAdapter.setItems(bookList)
            setHasFixedSize(true)
        }
    }
}

interface BookTypeInterface {
    fun onRowClick(item: BookListDb)
}



