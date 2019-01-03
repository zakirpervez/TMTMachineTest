package com.tmt.tmtmachinetest.feature.splash

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.tmt.tmtmachinetest.R
import com.tmt.tmtmachinetest.feature.BaseActivity
import com.tmt.tmtmachinetest.feature.main.MainActivity
import com.tmt.tmtmachinetest.util.ONE_SEC
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        askPermission()
    }

    private fun initialize() {
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.text_rotation)
        splashText.animation = rotateAnimation

        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                launchActivity()
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
    }

    private fun askPermission() {
        AndPermission
            .with(this)
            .runtime()
            .permission(Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS)
            .onGranted { permissions ->
                if (permissions.size == 2) {
                    initialize()
                } else {
                    askPermission()
                }
            }.onDenied { permissions ->
                if (permissions.size > 0) {
                    showToast("Please allow all permission otherwise your not able to proceed further")
                    askPermission()
                }
            }.start()
    }

    private fun launchActivity() {
        Handler().postDelayed({
            MainActivity.start(this@SplashActivity)
            finish()
        }, ONE_SEC * 2)
    }
}
