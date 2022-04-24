package com.egb.englishcentralbooks.ui.base

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import com.egb.englishcentralbooks.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal open class RoundedBottomSheetDialogFragment(
    @LayoutRes
    val layoutId: Int
) : BottomSheetDialogFragment() {

    open val isFullScreen = false
    open val isCoverContent = false

    private var dismissListener: (() -> Unit)? = null

    override fun getTheme(): Int = R.style.BottomSheetDialog



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)

        if (isFullScreen || isCoverContent) {
            dialog.setOnShowListener { d ->
                val bottomSheetDialog = d as BottomSheetDialog
                val parentLayout =
                    bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                parentLayout?.let { it ->
                    val behaviour = BottomSheetBehavior.from(it)
                    if (isFullScreen) {
                        setupFullHeight(it)
                    }
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setStyle(STYLE_NORMAL, theme)
        return inflater.inflate(layoutId, container, false)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.invoke()
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

}
