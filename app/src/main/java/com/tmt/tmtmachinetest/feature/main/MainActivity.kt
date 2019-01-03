package com.tmt.tmtmachinetest.feature.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.tmt.tmtmachinetest.R
import com.tmt.tmtmachinetest.feature.BaseActivity
import com.tmt.tmtmachinetest.pojo.ContactEntity
import com.tmt.tmtmachinetest.util.ContactCallback
import com.tmt.tmtmachinetest.util.ContactUtility

class MainActivity : BaseActivity(), ContactCallback, LifecycleOwner {


    private lateinit var contactUtility: ContactUtility
    private lateinit var lifecycleRegistry: LifecycleRegistry

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


    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    private fun initialize() {

        setToolbar("Home")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    override fun contactFetch(contacts: ArrayList<ContactEntity>) {
        if (contacts.size > 0) {
            showToast("Work Successfully completed")
        } else {
            showToast("Failure :  Need more work to do")
        }
    }
}
