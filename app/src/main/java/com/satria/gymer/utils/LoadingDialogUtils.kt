package com.satria.gymer.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import androidx.core.content.ContextCompat
import com.satria.gymer.databinding.LoadingDialogBinding

class LoadingDialogUtils(context: Context) {
    private val dialog: Dialog = Dialog(context)

    init {
        val binding = LoadingDialogBinding.inflate(LayoutInflater.from(context),null, false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(context, android.R.color.transparent)
        )
    }

    fun show() {
        if (!dialog.isShowing) dialog.show()
    }

    fun dismiss() {
        if (dialog.isShowing) dialog.dismiss()
    }
}
