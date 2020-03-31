package com.example.myrealtrip.views.detailview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myrealtrip.BR
import com.example.myrealtrip.R
import com.example.myrealtrip.databinding.FragmentDetailBinding
import com.example.myrealtrip.viewmodel.MainViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(){
    lateinit var model: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model=ViewModelProvider(activity!!.viewModelStore,ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=DataBindingUtil.inflate<FragmentDetailBinding>(inflater,R.layout.fragment_detail,container,false)
        binding.setVariable(BR.vm,model)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detail_webview.settings.javaScriptEnabled=true
        detail_webview.settings.loadWithOverviewMode=true
        detail_webview.webViewClient= object :WebViewClient(){

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                model.isWebViewLoading.postValue(true)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                model.isWebViewLoading.postValue(false)
            }
        }
        detail_webview.loadUrl(model.selectedNews.value?.url)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val keywords=model.selectedNews.value?.keywords
        keywords?.map {
            val chip= Chip(this.context)
            chip.text=it
            detail_keywords.addView(chip)
        }
        model.isWebViewLoading.observe(viewLifecycleOwner, Observer {
            detail_loading_view.visibility= if(it) View.VISIBLE else View.INVISIBLE
        })
    }
}