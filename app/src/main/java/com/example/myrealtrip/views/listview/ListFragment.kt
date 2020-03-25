package com.example.myrealtrip.views.listview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myrealtrip.R
import com.example.myrealtrip.databinding.FragmentListBinding
import com.example.myrealtrip.utils.MainViewModel
import com.example.myrealtrip.utils.rssutil.RssConnector
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment :Fragment(){
    lateinit var model:MainViewModel
    private val rssUrl="https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko"
//    private val rssUrl="https://news.google.com/rss"
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refresh_view.setOnRefreshListener { model.refreshData() }
        recycler_view.addItemDecoration(DividerItemDecoration(this.context,LinearLayout.VERTICAL))

        model.mList.observe(this, Observer { recycler_view.adapter?.notifyDataSetChanged() })
        model.isRefreshing.observe(this, Observer { refresh_view.isRefreshing=it })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        RssConnector(rssUrl,model).start()
    }
}