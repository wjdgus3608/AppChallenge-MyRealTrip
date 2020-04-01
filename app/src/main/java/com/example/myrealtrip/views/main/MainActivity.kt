package com.example.myrealtrip.views.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myrealtrip.R
import com.example.myrealtrip.databinding.ActivityMainBinding
import com.example.myrealtrip.viewmodel.MainViewModel
import com.example.myrealtrip.views.detailview.DetailFragment
import com.example.myrealtrip.views.listview.ListFragment


class MainActivity : AppCompatActivity() {
    lateinit var model: MainViewModel
    private var isClickedCurrent = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        model = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        binding.setVariable(androidx.databinding.library.baseAdapters.BR.vm, model)
        binding.setLifecycleOwner { this.lifecycle }
    }

    override fun onStart() {
        super.onStart()

        model.frgMode.observe(this, Observer {
            when (it) {
                0 -> supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right)
                    .replace(R.id.frg_container, ListFragment()).commit()
                1 -> supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.frg_container, DetailFragment()).commit()
            }
        })
        model.toastMsg.observe(this, Observer { if(!it.isNullOrEmpty()) Toast.makeText(this, it, Toast.LENGTH_SHORT).show() })
    }

    override fun onBackPressed() {
        if (model.frgMode.value == 1) {
            model.frgMode.postValue(0)
            model.selectedNews.postValue(null)
        } else {
            if (isClickedCurrent) {
                super.onBackPressed()
            }
            isClickedCurrent = true
            model.toastMsg.postValue("한번더 누르면 앱이 종료됩니다.")
            Handler().postDelayed({ isClickedCurrent = false }, 2000)
        }
    }

}
