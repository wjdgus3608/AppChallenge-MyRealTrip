package com.example.myrealtrip.views.listview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrealtrip.R
import com.example.myrealtrip.databinding.FragmentListBinding
import com.example.myrealtrip.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlin.coroutines.resume

class ListFragment :Fragment(){
    lateinit var model: MainViewModel
    var isLoading:Boolean=false
    private val url="https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko"
//    private val url="https://news.google.com/rss"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("log","onCreate")
        model=ViewModelProvider(activity!!.viewModelStore,ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("log","onCreateView")
        val binding=DataBindingUtil.inflate<FragmentListBinding>(inflater, R.layout.fragment_list,container,false)
        binding.setVariable(com.example.myrealtrip.BR.vm,model)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("log","onViewCreated")

        refresh_view.setOnRefreshListener { model.requestData(url) }
        recycler_view.addItemDecoration(DividerItemDecoration(this.context,LinearLayout.VERTICAL))
        recycler_view.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager=recyclerView.layoutManager as LinearLayoutManager
                val itemCnt=layoutManager.itemCount
                val lastItemIndex=layoutManager.findLastVisibleItemPosition()
                if(!isLoading && lastItemIndex >= itemCnt-1 && model.isDataEnd.value==false){
                    Log.e("log","more load!!")
                    isLoading=true
                    model.isLoading.postValue(true)
                    model.continuation.value?.resume("Resumed")
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model.mList.observe(viewLifecycleOwner, Observer {
            if(it.size==0) model.requestData(url)
            recycler_view.adapter?.notifyDataSetChanged()
        })
        model.isLoading.observe(viewLifecycleOwner, Observer {
            refresh_view.isRefreshing=it
            isLoading=it
        })

    }

}