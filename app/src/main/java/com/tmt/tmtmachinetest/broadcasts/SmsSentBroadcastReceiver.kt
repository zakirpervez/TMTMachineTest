package com.tmt.tmtmachinetest.broadcasts

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.widget.Toast

object SmsSentBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                showToast(context, "SMS Sent")
            }

            SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                showToast(context, "Generic failure")
            }

            SmsManager.RESULT_ERROR_NO_SERVICE -> {
                showToast(context, "No service")
            }

            SmsManager.RESULT_ERROR_NULL_PDU -> {
                showToast(context, "Null PDU")
            }

            SmsManager.RESULT_ERROR_RADIO_OFF -> {
                showToast(context, "Radio off")
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