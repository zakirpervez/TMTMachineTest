package com.tmt.tmtmachinetest

import com.codemonkeylabs.fpslibrary.TinyDancer
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class TmtDebugApplication : TmtApplication() {

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        if (LeakCanary.isInAnalyzerProcess(this)) return
        else LeakCanary.install(this)
        TinyDancer.create()
            .redFlagPercentage(.1f) // set red indicator for 10%....different from default
            .startingXPosition(200)
            .startingYPosition(600)
            .show(this)

        Timber.plant(Timber.DebugTree())
    }

}