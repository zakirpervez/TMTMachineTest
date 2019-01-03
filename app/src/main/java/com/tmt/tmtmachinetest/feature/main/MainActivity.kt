package com.tmt.tmtmachinetest.feature.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.tmt.tmtmachinetest.R
import com.tmt.tmtmachinetest.feature.BaseActivity

class MainActivity : BaseActivity() {

    companion object {
        fun start(from: AppCompatActivity) {
            val intent = Intent(from, MainActivity::class.java)
            from.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
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
}
