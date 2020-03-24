package com.example.myrealtrip.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myrealtrip.BR
import com.example.myrealtrip.utils.MainViewModel
import com.example.myrealtrip.R
import com.example.myrealtrip.databinding.ActivityMainBinding
import com.example.myrealtrip.views.detailview.DetailFragment
import com.example.myrealtrip.views.listview.ListFragment

class MainActivity : AppCompatActivity() {
    lateinit var model:MainViewModel
    private var isClickedCurrent=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        model=ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        binding.setVariable(androidx.databinding.library.baseAdapters.BR.vm,model)
        binding.setLifecycleOwner { this.lifecycle }
        model.frgMode.observe(this, Observer {
            when(it){
                0->supportFragmentManager.beginTransaction().replace(R.id.frg_container,ListFragment()).commit()
                1->supportFragmentManager.beginTransaction().replace(R.id.frg_container,DetailFragment()).addToBackStack(null).commit()
            }
        })

    }

    override fun onBackPressed() {
        if(model.frgMode.value==1) {
            model.frgMode.postValue(0)
            super.onBackPressed()
        }
        else {
            if (isClickedCurrent) {
                super.onBackPressed()
            }
            isClickedCurrent = true
            Toast.makeText(this, "한번더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ isClickedCurrent = false }, 2000)
        }
    }
}
