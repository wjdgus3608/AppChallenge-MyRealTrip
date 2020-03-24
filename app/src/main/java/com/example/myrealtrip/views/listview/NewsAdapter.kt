package com.example.myrealtrip.views.listview

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myrealtrip.R
import com.example.myrealtrip.databinding.NewsItemBinding
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.utils.MainViewModel
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter(parentModel:MainViewModel) :RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){
    var mList=MutableLiveData<ArrayList<NewsItem>>()
    val model = parentModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder{
        val inflater=LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<NewsItemBinding>(inflater,
            R.layout.news_item,parent,false)
        binding.setVariable(BR.vm,model)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int = mList.value!!.size

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        mList.value!![position].let { item -> with(holder){
            newsTitle.text=item.title
            newsDes.text=item.des
        }}
        holder.itemView.setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                model.selectedNews.postValue(mList.value!![position])
                model.frgMode.postValue(1)
                return false
            }
        })
    }
    inner class NewsViewHolder(binding:NewsItemBinding):RecyclerView.ViewHolder(binding.root){
        val newsTitle=itemView.news_title
        val newsDes=itemView.news_des
        val keywordContainer=itemView.keyword_container
    }
}