package com.example.myrealtrip.views.splash

import android.os.Bundle
import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.myrealtrip.R
import com.example.myrealtrip.views.main.MainActivity

class Splash :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val hd=Handler()
        hd.postDelayed(SplashHandler(),1300)
    }

    override fun onBackPressed() {

    }

    inner class SplashHandler : Runnable{
        override fun run() {
            startActivity(Intent(this@Splash,
                MainActivity::class.java))
            this@Splash.finish()
        }
    }
}