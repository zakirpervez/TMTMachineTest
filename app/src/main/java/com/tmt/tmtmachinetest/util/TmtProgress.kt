package com.tmt.tmtmachinetest.util

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.Window
import com.tmt.tmtmachinetest.R

open class TmtProgress(val context: Context) {

    private var alertDialog: AlertDialog

    init {
        val alertDialogBuilder = AlertDialog.Builder(context, R.style.AlertDialogCustom)
        alertDialogBuilder.setView(R.layout.progress_loader_layout)
        alertDialog = alertDialogBuilder.create()
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCancelable(false)
    }

    fun show() {
        alertDialog.show()
    }

    fun dismiss() {
        alertDialog.dismiss()
    }
}