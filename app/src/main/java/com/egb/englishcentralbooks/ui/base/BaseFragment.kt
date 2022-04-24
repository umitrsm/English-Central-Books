package com.egb.englishcentralbooks.ui.base

import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.egb.englishcentralbooks.R
import com.egb.englishcentralbooks.ui.MainActivity
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    private var toolbar: Toolbar? = null

    var title: CharSequence? = null
        set(value) {
            field = value
            updateTitle()
        }

    private fun updateTitle() {
        toolbar?.findViewById<TextView>(R.id.toolbarTitle)?.text = title
    }

    @CallSuper
    open fun initToolbar(toolbar: MaterialToolbar) {
        initToolbar(toolbar, true)
    }

    @CallSuper
    open fun initToolbar(toolbar: MaterialToolbar, showAlwaysBackButton: Boolean) {
        this.toolbar = toolbar
        updateTitle()
    }

    internal fun Fragment.showDialogFragment(dialogFragment: DialogFragment, tag: String?) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED).not()) return
        (activity as? MainActivity)?.showDialogFragment(dialogFragment, tag)
    }

    private fun FragmentActivity.showDialogFragment(dialogFragment: DialogFragment, tag: String?) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED).not()) return
        val fragmentManager = supportFragmentManager
        dialogFragment.show(fragmentManager, tag)
    }

    internal fun Fragment.navigateFragment(fragment: Fragment, tag: String?) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED).not()) return
        val transaction = (activity as? MainActivity)?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentContainer, fragment)
        transaction?.addToBackStack(tag)
        transaction?.commit()
    }

    internal fun Fragment.onBackPressed() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED).not()) return
        (activity as? MainActivity)?.supportFragmentManager?.popBackStack()
    }
}