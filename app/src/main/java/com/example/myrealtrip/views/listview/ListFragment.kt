package com.example.myrealtrip.views.listview

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

class ListFragment : Fragment() {
    lateinit var model: MainViewModel
    var isLoading: Boolean = false
    private val url="https://news.google.com/rss"
    private var isNetworkConnected=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(
            activity!!.viewModelStore,
            ViewModelProvider.NewInstanceFactory()
        ).get(
            MainViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentListBinding>(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )
        binding.setVariable(com.example.myrealtrip.BR.vm, model)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isNetworkConnected=checkInternet()
        refresh_view.setOnRefreshListener {
            isNetworkConnected=checkInternet()
            if(isNetworkConnected)
                model.requestData(url)
        }
        recycler_view.addItemDecoration(DividerItemDecoration(this.context, LinearLayout.VERTICAL))
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val itemCnt = layoutManager.itemCount
                val lastItemIndex = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastItemIndex >= itemCnt - 1 && itemCnt!=0 && model.isDataEnd.value == false) {
                    isNetworkConnected=checkInternet()
                    if(isNetworkConnected) {
                        Log.d("log", "more load!!")
                        isLoading = true
                        model.isLoading.postValue(true)
                        model.continuation.value?.resume("Resumed")
                    }
                }

            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model.mList.observe(viewLifecycleOwner, Observer {
            if (it.size == 0 && isNetworkConnected) model.requestData(url)
            recycler_view.adapter?.notifyDataSetChanged()
        })
        model.isLoading.observe(viewLifecycleOwner, Observer {
            refresh_view.isRefreshing = it
            isLoading = it
        })
    }

    fun checkInternet():Boolean{
        val isConnected=isInternetAvailable(this.context!!)
        if(!isConnected) {
            model.isLoading.postValue(false)
            model.toastMsg.postValue("인터넷 연결상태를 확인해주세요.")
        }
        return isConnected
    }

    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

}