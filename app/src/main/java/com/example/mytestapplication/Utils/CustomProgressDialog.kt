package com.example.mytestapplication.Utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import com.example.mytestapplication.R

class CustomProgressDialog {

    companion object {
        var mDialog: Dialog? = null

        fun loadingProgress(activity: Context) {
            if (mDialog != null)
                if (mDialog?.isShowing!!) {
                    return
                }

            mDialog = Dialog(activity)
            mDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            mDialog?.setContentView(R.layout.dialog_progress)
            mDialog?.window?.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            mDialog!!.setTitle("Synchronizing...")
            mDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog?.setCanceledOnTouchOutside(false)
            mDialog?.setCancelable(false)
            if (!mDialog?.isShowing!!)
                mDialog?.show()
        }

        fun dismissProgress() {
            try {
                if (mDialog != null)
                    if (mDialog?.isShowing!!)
                        mDialog?.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}