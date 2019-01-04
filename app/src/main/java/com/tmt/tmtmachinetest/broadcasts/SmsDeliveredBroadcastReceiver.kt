package com.tmt.tmtmachinetest.broadcasts

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

object SmsDeliveredBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when(resultCode){
            Activity.RESULT_OK -> {
                showToast(context, "SMS Delivered")
            }

            Activity.RESULT_CANCELED->{
                showToast(context, "SMS Not Delivered")
            }

            else -> {
                showToast(context, "Something went wrong")
            }
        }
    }

    private fun showToast(context: Context?, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}