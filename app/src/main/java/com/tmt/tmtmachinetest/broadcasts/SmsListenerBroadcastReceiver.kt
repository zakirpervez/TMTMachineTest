package com.tmt.tmtmachinetest.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import com.tmt.tmtmachinetest.util.SMSCallback


private const val messageBody = "HELLO 123456"

class SmsListenerBroadcastReceiver(private val smsCallback: SMSCallback) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val dataBundle = intent!!.extras!!
        val pdus = dataBundle.get("pdus") as Array<Any>

        for (i in 0 until pdus.size) {
            val pdu: ByteArray = pdus[i] as ByteArray
            val smsMessage = SmsMessage.createFromPdu(pdu)
            val sender = smsMessage.displayOriginatingAddress
            if (smsMessage.messageBody == messageBody) {
                smsCallback.receiveSms(sender, smsMessage.messageBody)
                break
            }
        }
    }
}