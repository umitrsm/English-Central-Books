package com.egb.englishcentralbooks.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.egb.englishcentralbooks.R
import com.egb.englishcentralbooks.databinding.ActivityMainBinding
import com.egb.englishcentralbooks.ui.books.BooksHomeFragment
import com.egb.englishcentralbooks.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val fragmentContainer = R.id.fragmentContainer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(fragmentContainer, BooksHomeFragment.newInstance())
            }
        }
    }
}