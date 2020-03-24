package com.example.myrealtrip.views.detailview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myrealtrip.BR
import com.example.myrealtrip.R
import com.example.myrealtrip.databinding.FragmentDetailBinding
import com.example.myrealtrip.utils.MainViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(){
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
        val binding=DataBindingUtil.inflate<FragmentDetailBinding>(inflater,R.layout.fragment_detail,container,false)
        binding.setVariable(BR.vm,model)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detail_webview.settings.javaScriptEnabled=true
        detail_webview.settings.loadWithOverviewMode=true
        detail_webview.webViewClient= WebViewClient()
        detail_webview.loadUrl(model.selectedNews.value!!.url)
    }
}