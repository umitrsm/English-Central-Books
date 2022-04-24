package com.egb.englishcentralbooks.ui.bookDetail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.egb.englishcentralbooks.R
import com.egb.englishcentralbooks.databinding.FragmentBookDetailBinding
import com.egb.englishcentralbooks.db.entity.BookDb
import com.egb.englishcentralbooks.extensions.argument
import com.egb.englishcentralbooks.extensions.loadImageUrl
import com.egb.englishcentralbooks.extensions.logInfo
import com.egb.englishcentralbooks.ui.base.BaseFragment
import com.egb.englishcentralbooks.utils.viewBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : BaseFragment(R.layout.fragment_book_detail) {

    companion object {
        private const val BOOK_KEY = "BOOK_KEY"

        fun newInstance(bookDetail: List<BookDb>) = BookDetailFragment().apply {
            arguments = bundleOf(BOOK_KEY to bookDetail)
        }
    }

    private val binding by viewBinding(FragmentBookDetailBinding::bind)
    private val bookDetail: List<BookDb>? by argument(BOOK_KEY)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = bookDetail?.firstOrNull()?.title
        logInfo("deneme  " + bookDetail.toString())
        initToolbar(binding.appbar.toolbar)

        initializeUI()
    }

    override fun initToolbar(toolbar: MaterialToolbar) {
        super.initToolbar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationIconTint(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        toolbar.inflateMenu(R.menu.layout_direction)
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.switch_direction) {
                if (view?.layoutDirection != View.LAYOUT_DIRECTION_RTL) {
                    view?.layoutDirection = View.LAYOUT_DIRECTION_RTL
                } else {
                    view?.layoutDirection = View.LAYOUT_DIRECTION_LTR
                }
            }
            true
        }
    }

    private fun initializeUI() {
        binding.bookImage.loadImageUrl(bookDetail?.firstOrNull()?.bookImage)
        binding.bookName.text = bookDetail?.firstOrNull()?.title
        binding.bookAuthor.text = bookDetail?.firstOrNull()?.author
        binding.bookDescription.text = bookDetail?.firstOrNull()?.description
        binding.bookAge.text = bookDetail?.firstOrNull()?.ageGroup
        binding.bookContributor.text = bookDetail?.firstOrNull()?.contributor
        binding.bookPrice.text = bookDetail?.firstOrNull()?.price
    }

}