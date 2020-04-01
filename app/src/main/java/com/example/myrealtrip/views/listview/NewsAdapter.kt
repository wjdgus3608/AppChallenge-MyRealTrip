package com.example.myrealtrip.views.listview

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
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
import com.example.myrealtrip.viewmodel.MainViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.news_item.view.*
import java.util.*

class NewsAdapter(parentModel: MainViewModel) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    var mList = MutableLiveData<Vector<NewsItem>>()
    val model = parentModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<NewsItemBinding>(
            inflater,
            R.layout.news_item, parent, false
        )
        binding.setVariable(BR.vm, model)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int = mList.value?.size ?: 0

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        mList.value?.get(position)?.let { item ->
            with(holder) {
                newsTitle.text = if (item.title.isEmpty()) "타이틀을 불러오는데 실패했습니다." else item.title
                newsDes.text = if (item.des.isEmpty()) "본문을 불러오는데 실패했습니다." else item.des
                if (item.img.isEmpty()) {
                    loadingView.visibility = View.GONE
                    newsImg.setImageResource(R.drawable.no_picture)
                } else {
                    Glide.with(holder.itemView).load(item.img)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                loadingView.visibility = View.GONE
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
                                loadingView.visibility = View.GONE
                                return false
                            }
                        }).into(newsImg)
                }

                keywordContainer.removeAllViews()
                item.keywords?.map {
                    val chip = Chip(holder.itemView.context)
                    chip.text = it
                    chip.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    chip.rippleColor = null
                    keywordContainer.addView(chip)
                }
            }
        }
    }

    inner class NewsViewHolder(binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val newsTitle: TextView = itemView.news_title
        val newsDes: TextView = itemView.news_des
        val newsImg: ImageView = itemView.news_img
        val keywordContainer: ChipGroup = itemView.keyword_container
        val loadingView: ProgressBar = itemView.item_loading_view

        init {
            itemView.setOnClickListener {
                model.selectedNews.postValue(mList.value?.get(layoutPosition))
                model.frgMode.postValue(1)
                Log.d("log", "$layoutPosition is clicked!")
            }
        }
    }
}