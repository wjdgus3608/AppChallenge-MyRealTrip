package com.example.myrealtrip.views.listview

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myrealtrip.R
import com.example.myrealtrip.databinding.NewsItemBinding
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.utils.MainViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
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
        mList.value?:return 0
        return mList.value!!.size
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        mList.value!![position].let { item -> with(holder){

            newsTitle.text = if (item.title.isEmpty()) "타이틀을 불러오는데 실패했습니다." else item.title
            newsDes.text = if (item.des.isEmpty()) "본문을 불러오는데 실패했습니다." else item.des
            Glide.with(holder.itemView).load(item.img).listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    newsImg.setImageResource(R.drawable.no_picture)
                    return false
                }
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).into(newsImg)

            keywordContainer.removeAllViews()
            item.keywords?.map {
                val chip=Chip(holder.itemView.context)
                chip.text=it
                keywordContainer.addView(chip)
            }
        }}
    }
    inner class NewsViewHolder(binding:NewsItemBinding):RecyclerView.ViewHolder(binding.root){
        val newsTitle:TextView=itemView.news_title
        val newsDes:TextView=itemView.news_des
        val newsImg:ImageView=itemView.news_img
        val keywordContainer:ChipGroup=itemView.keyword_container
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