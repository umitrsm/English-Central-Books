package com.egb.englishcentralbooks.ui.books

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.egb.englishcentralbooks.R
import com.egb.englishcentralbooks.databinding.FragmentBooksHomeBinding
import com.egb.englishcentralbooks.db.entity.BookDb
import com.egb.englishcentralbooks.db.entity.BookListDb
import com.egb.englishcentralbooks.extensions.toast
import com.egb.englishcentralbooks.network.datasource.Resource
import com.egb.englishcentralbooks.ui.base.BaseFragment
import com.egb.englishcentralbooks.ui.bookDetail.BookDetailFragment
import com.egb.englishcentralbooks.ui.books.adapter.BooksAdapter
import com.egb.englishcentralbooks.ui.books.repository.BooksMapper
import com.egb.englishcentralbooks.ui.favoriteBooks.FavoriteBooksFragment
import com.egb.englishcentralbooks.utils.viewBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BooksHomeFragment : BaseFragment(R.layout.fragment_books_home), BooksMapper {

    companion object {
        fun newInstance() = BooksHomeFragment()
    }

    private val binding by viewBinding(FragmentBooksHomeBinding::bind)

    private val viewModel: BooksViewModel by viewModels()

    private val bookAdapter by lazy(LazyThreadSafetyMode.NONE) {
        BooksAdapter(addAction = {
            it.apply {
                viewModel.addFav(it)
            }
        }, deleteAction = {
            it.apply {
                viewModel.deleteFav(it)
            }
        }, openDetail = {
            val bookDetail: List<BookDb> = listOf(it)
            navigateFragment(BookDetailFragment.newInstance(bookDetail), "")
        }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = getString(R.string.library)
        initToolbar(binding.appbar.toolbar)


        initialize()
        observers()
        listeners()
    }

    override fun initToolbar(toolbar: MaterialToolbar) {
        super.initToolbar(toolbar)
        toolbar.inflateMenu(R.menu.favorites)
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.favorite) {
                if (viewModel.favs.value?.isNotEmpty() == true) {
                    val favSheet = FavoriteBooksFragment.newInstance(viewModel.favs.value as List<BookDb>)
                    favSheet.addBookToFavList {
                        it.let {
                            viewModel.addFav(it)
                        }
                    }
                    favSheet.deleteBookFromFavList {
                        it.let {
                            viewModel.deleteFav(it)
                        }
                    }
                    showDialogFragment(favSheet, "Favs")
                } else {
                    toast(getString(R.string.error_message_favorites), this.requireContext())
                    viewModel.fetchFavList()
                }
            } else if (menuItem.itemId == R.id.switch_direction) {
                if (view?.layoutDirection != View.LAYOUT_DIRECTION_RTL) {
                    view?.layoutDirection = View.LAYOUT_DIRECTION_RTL
                } else {
                    view?.layoutDirection = View.LAYOUT_DIRECTION_LTR
                }
            }
            true
        }
    }

    private fun initialize() {
        viewModel.fetchBooksList()
        viewModel.fetchFavList()
        binding.bookRecyclerView.apply {
            adapter = bookAdapter
            setHasFixedSize(true)
        }
    }

    private fun listeners() {
        binding.bookTypeSelection.setOnClickListener { _ ->
            viewModel.bookList.value?.let { bookList ->
                if (bookList is Resource.Success) {
                    bookList.data?.let { setClickListener(it) }
                } else if (bookList is Resource.Error) {
                    toast(bookList.message, context)
                    bookList.data?.let { setClickListener(it) }
                }
            }
        }
    }

    private fun setClickListener(bookTypeList: List<BookListDb>) {
        bookTypeList.let {
            BottomSheetListFragment.newInstance(it, bookTypeInterface = object : BookTypeInterface {
                override fun onRowClick(item: BookListDb) {
                    viewModel.selectBookType(item)
                }
            }).let { sheet ->
                showDialogFragment(sheet, "List")
            }
        }
    }

    private fun observers() {
        viewModel.bookList.observe(viewLifecycleOwner) {
            if (it is Resource.Success) {
                binding.errorMessage.isVisible = false
                binding.bookTypeSelection.isVisible = true
                it.data?.firstOrNull()?.let { bookType -> viewModel.selectBookType(bookType) }
            } else if (it is Resource.Error) {
                val listInfo = if (it.data.isNullOrEmpty()) "\n No Data in Local Storage" else ""
                toast(it.message + listInfo, context)
                it.data?.firstOrNull()?.let { bookType -> viewModel.selectBookType(bookType) }
                if (it.data.isNullOrEmpty()) {
                    binding.bookTypeSelection.isVisible = false
                    binding.errorMessage.isVisible = true
                } else {
                    binding.bookTypeSelection.isVisible = true
                    binding.errorMessage.isVisible = false
                }
            }
        }


        viewModel.selectedBookType.observe(viewLifecycleOwner) {
            binding.selectedTypeView.text = it.display_name
        }

        viewModel.books.observe(viewLifecycleOwner) {
            if (it is Resource.Success) {
                bookAdapter.setItems(it.data)
            } else if (it is Resource.Error) {
                val listInfo = if (it.data.isNullOrEmpty()) "\n No Data in Local Storage" else ""
                toast(it.message + listInfo, context)
                bookAdapter.setItems(it.data)
            }
        }
    }
}