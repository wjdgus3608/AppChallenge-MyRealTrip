package com.example.myrealtrip.views.listview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myrealtrip.R
import com.example.myrealtrip.databinding.FragmentListBinding
import com.example.myrealtrip.utils.MainViewModel

class ListFragment :Fragment(){
    lateinit var model:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model=ViewModelProvider(activity!!.viewModelStore,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=DataBindingUtil.inflate<FragmentListBinding>(inflater, R.layout.fragment_list,container,false)
        binding.setVariable(com.example.myrealtrip.BR.vm,model)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}