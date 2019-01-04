package com.tmt.tmtmachinetest.feature.main

import android.app.PendingIntent
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.telephony.SmsManager
import android.view.MenuItem
import android.view.View
import com.tmt.tmtmachinetest.R
import com.tmt.tmtmachinetest.broadcasts.SmsDeliveredBroadcastReceiver
import com.tmt.tmtmachinetest.broadcasts.SmsListenerBroadcastReceiver
import com.tmt.tmtmachinetest.broadcasts.SmsSentBroadcastReceiver
import com.tmt.tmtmachinetest.feature.BaseActivity
import com.tmt.tmtmachinetest.feature.main.adapter.ContactAdapter
import com.tmt.tmtmachinetest.pojo.ContactEntity
import com.tmt.tmtmachinetest.util.*
import kotlinx.android.synthetic.main.activity_main.*

private const val delivered = "DELIVERED"
private const val sent = "SENT"
private const val receive = "RECEIVE"

class MainActivity : BaseActivity(), ContactCallback, LifecycleOwner, SMSCallback {

    private lateinit var contactUtility: ContactUtility
    private lateinit var lifecycleRegistry: LifecycleRegistry
    private lateinit var tmtProgress: TmtProgress
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var smsListenerBroadcastReceiver: SmsListenerBroadcastReceiver

    companion object {
        fun start(from: AppCompatActivity) {
            val intent = Intent(from, MainActivity::class.java)
            from.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contactUtility = ContactUtility(this, this)
        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.addObserver(contactUtility)

        initialize()
    }


    override fun onStop() {
        unregisterReceiver(SmsSentBroadcastReceiver)
        unregisterReceiver(SmsDeliveredBroadcastReceiver)
        unregisterReceiver(smsListenerBroadcastReceiver)
        super.onStop()
    }


    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    private fun initialize() {
        tmtProgress = TmtProgress(this)
        setToolbar("Home")
        tmtProgress.show()
        contactAdapter = ContactAdapter(this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecorator = DividerItemDecoration(this, layoutManager.orientation)
        contactRv.layoutManager = layoutManager
        contactRv.addItemDecoration(itemDecorator)
        contactRv.adapter = contactAdapter
        smsListenerBroadcastReceiver = SmsListenerBroadcastReceiver(this)

        registerReceiver(SmsSentBroadcastReceiver, IntentFilter(delivered))
        registerReceiver(SmsDeliveredBroadcastReceiver, IntentFilter(sent))
        registerReceiver(smsListenerBroadcastReceiver, IntentFilter(receive))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    override fun contactFetch(contacts: ArrayList<ContactEntity>) {
        if (contacts.size > 0) {
            contactRv.visibility = View.VISIBLE
            noContactText.visibility = View.GONE
            contactAdapter.update(contacts)
        } else {
            contactRv.visibility = View.GONE
            noContactText.visibility = View.VISIBLE
        }
        tmtProgress.dismiss()
    }

    override fun sendSms(mobile: String, text: String) {
        val smsManager: SmsManager = SmsManager.getDefault()

        val piSend = PendingIntent.getBroadcast(this, 0, Intent(sent), 0)
        val piDelivered = PendingIntent.getBroadcast(this, 0, Intent(delivered), 0)

        smsManager.sendTextMessage(mobile, null, text, piSend, piDelivered)
    }

    override fun receiveSms(mobile: String, text: String) {
        showToast("$mobile : $text")
    }
}
