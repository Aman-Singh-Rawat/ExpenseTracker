package com.internship.expensetracker.core

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.internship.expensetracker.R

open class BaseFragment: Fragment() {
    internal var progressDialog: Dialog? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    internal fun showProgress() {
        if (progressDialog?.isShowing != true) {
            progressDialog = Dialog(requireActivity())
            progressDialog?.let {
                it.setContentView(R.layout.progress_dialog_layout)
                it.setCancelable(false)
                it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                it.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
                it.show()
            }
        }
    }

    internal fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    internal fun hideProgress() {
        progressDialog?.cancel()
    }
}