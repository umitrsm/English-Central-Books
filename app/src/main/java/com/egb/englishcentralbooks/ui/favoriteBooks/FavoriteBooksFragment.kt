package com.egb.englishcentralbooks.ui.favoriteBooks

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.egb.englishcentralbooks.R
import com.egb.englishcentralbooks.databinding.FragmentBooksFavoritesBinding
import com.egb.englishcentralbooks.db.entity.BookDb
import com.egb.englishcentralbooks.extensions.argument
import com.egb.englishcentralbooks.extensions.toast
import com.egb.englishcentralbooks.ui.base.RoundedBottomSheetDialogFragment
import com.egb.englishcentralbooks.ui.books.adapter.BooksAdapter
import com.egb.englishcentralbooks.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class FavoriteBooksFragment : RoundedBottomSheetDialogFragment(R.layout.fragment_books_favorites) {

    companion object {
        private const val FAV_BOOKS = "FAV_BOOKS"

        fun newInstance(favBooks: List<BookDb>) = FavoriteBooksFragment().apply {
            arguments = bundleOf(FAV_BOOKS to favBooks)
        }
    }

    private val binding by viewBinding(FragmentBooksFavoritesBinding::bind)
    private val favBooks: List<BookDb>? by argument(FAV_BOOKS)

    private var deleteListener: ((BookDb) -> Unit)? = null
    private var addListener: ((BookDb) -> Unit)? = null

    private val bookAdapter by lazy(LazyThreadSafetyMode.NONE) {
        BooksAdapter(addAction = {
            addListener?.invoke(it)
            toast("Book will be added to Favorite List soon", this.requireContext())
        }, deleteAction = {
            deleteListener?.invoke(it)
            toast("Book will be deleted from Favorite List soon", this.requireContext())
        }, openDetail = {

        }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        binding.bookRecyclerView.apply {
            adapter = bookAdapter
            bookAdapter.setItems(favBooks)
            setHasFixedSize(true)
        }
    }


    fun addBookToFavList(listener: ((BookDb) -> Unit)?) {
        addListener = listener
    }

    fun deleteBookFromFavList(listener: ((BookDb) -> Unit)?) {
        deleteListener = listener
    }

}