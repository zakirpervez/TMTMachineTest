package com.tmt.tmtmachinetest.feature.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.tmt.tmtmachinetest.R
import com.tmt.tmtmachinetest.feature.BaseActivity
import com.tmt.tmtmachinetest.feature.main.adapter.ContactAdapter
import com.tmt.tmtmachinetest.pojo.ContactEntity
import com.tmt.tmtmachinetest.util.ContactCallback
import com.tmt.tmtmachinetest.util.ContactUtility
import com.tmt.tmtmachinetest.util.TmtProgress
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ContactCallback, LifecycleOwner {


    private lateinit var contactUtility: ContactUtility
    private lateinit var lifecycleRegistry: LifecycleRegistry
    private lateinit var tmtProgress: TmtProgress
    private val contactAdapter = ContactAdapter()

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
        tmtProgress = TmtProgress(this)
        setToolbar("Home")
        tmtProgress.show()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecorator = DividerItemDecoration(this, layoutManager.orientation)
        contactRv.layoutManager = layoutManager
        contactRv.addItemDecoration(itemDecorator)
        contactRv.adapter = contactAdapter
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
}
