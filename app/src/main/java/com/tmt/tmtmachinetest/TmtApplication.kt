package com.tmt.tmtmachinetest

import android.app.Application
import android.content.Context

open class TmtApplication: Application() {

    companion object {
        @JvmStatic
        var appContext : Context? = null
    }

    override fun onCreate() {
        appContext = applicationContext
        super.onCreate()
    }
}