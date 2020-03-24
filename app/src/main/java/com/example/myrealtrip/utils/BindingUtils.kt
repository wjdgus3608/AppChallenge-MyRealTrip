package com.example.myrealtrip.utils

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.views.listview.NewsAdapter

@BindingAdapter("bind_items","bind_model")
fun setBindiItems(view : RecyclerView, mList : MutableLiveData<ArrayList<NewsItem>>, model: MainViewModel) {
    val adapter = view.adapter as? NewsAdapter
        ?: NewsAdapter(model).apply { view.adapter = this }
    adapter.mList = mList
    adapter.notifyDataSetChanged()
}