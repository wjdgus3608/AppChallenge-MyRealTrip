package com.example.myrealtrip

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity




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
            startActivity(Intent(this@Splash,MainActivity::class.java))
            this@Splash.finish()
        }
    }
}