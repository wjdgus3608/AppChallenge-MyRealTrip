package com.example.myrealtrip.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myrealtrip.BR
import com.example.myrealtrip.utils.MainViewModel
import com.example.myrealtrip.R
import com.example.myrealtrip.databinding.ActivityMainBinding
import com.example.myrealtrip.views.listview.ListFragment

class MainActivity : AppCompatActivity() {
    lateinit var model:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding=DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        model=ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        binding.setVariable(androidx.databinding.library.baseAdapters.BR.vm,model)
        binding.setLifecycleOwner { this.lifecycle }
        model.frgMode.observe(this, Observer {
            when(it){
                0->supportFragmentManager.beginTransaction().replace(R.id.frg_container,ListFragment()).commit()
            }
        })

    }
}
