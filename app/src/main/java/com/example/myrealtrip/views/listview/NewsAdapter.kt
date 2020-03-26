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
import com.bumptech.glide.Glide
import com.example.myrealtrip.R
import com.example.myrealtrip.databinding.NewsItemBinding
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.utils.MainViewModel
import com.google.android.material.chip.Chip
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

    override fun getItemCount(): Int {
        if(mList.value==null) return 0
        else return mList.value!!.size
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        mList.value!![position].let { item -> with(holder){
            newsTitle.text=item.title
            newsDes.text=item.des
            Glide.with(holder.itemView).load(item.img).into(newsImg)
            keywordContainer.removeAllViews()
            item.keywords?.map {
                val chip=Chip(holder.itemView.context)
                chip.text=it
                keywordContainer.addView(chip)
            }
        }}
    }
    inner class NewsViewHolder(binding:NewsItemBinding):RecyclerView.ViewHolder(binding.root){
        val newsTitle=itemView.news_title
        val newsDes=itemView.news_des
        val newsImg=itemView.news_img
        val keywordContainer=itemView.keyword_container
        init {
            itemView.setOnClickListener (object :View.OnClickListener{
                override fun onClick(v: View?) {
                    model.selectedNews.postValue(mList.value!![layoutPosition])
                    model.frgMode.postValue(1)
                    Log.e("log","$layoutPosition is clicked!")
                }
            })
        }
    }
}