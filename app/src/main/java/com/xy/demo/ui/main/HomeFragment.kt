package com.xy.demo.ui.main

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.xy.demo.R
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.databinding.FragmentHomeBinding
import com.xy.demo.model.VideoStoreModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.main.adapter.HomeAdapter
import com.xy.demo.ui.main.viewModel.MainViewModel
import com.xy.demo.ui.video.VideoPlayActivity
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.transformer.DepthPageTransformer

class HomeFragment : MBBaseFragment<FragmentHomeBinding, MainViewModel>() {

    val homeAdapter = HomeAdapter()


    var bannerView: Banner<VideoStoreModel.BannerDTO, BannerImageAdapter<VideoStoreModel.BannerDTO>>? =
        null

    override fun getLayoutId(): Int {
        return R.layout.fragment_home;
    }

    override fun initView() {

        initRecyclerView(binding.recyclerView, LinearLayoutManager.VERTICAL, 0)

        binding.recyclerView.adapter = homeAdapter

        val bannerLin = layoutInflater.inflate(R.layout.include_banner, null)
        bannerView = bannerLin.findViewById(R.id.bannerView)
        homeAdapter.addHeaderView(bannerLin)
        binding.refreshView.setEnableLoadMore(false)
        binding.refreshView.setOnRefreshListener { initArgument() }
    }


    override fun initArgument() {
        super.initArgument()
//        readerParams?.let { viewModel.getCheckSetting(it) }
        readerParams?.let { viewModel.getVideoStore(it) }
    }


    override fun initViewObservable() {
        super.initViewObservable()

        viewModel.videoStoreModel.observe(this) {
            homeAdapter.setNewInstance(it.label)
            setBannerLin(it)
        }
    }


    fun setBannerLin(bean: VideoStoreModel) {


        bannerView!!.setAdapter(object :
            BannerImageAdapter<VideoStoreModel.BannerDTO>(bean.banner) {

            override fun onBindView(
                holder: BannerImageHolder,
                data: VideoStoreModel.BannerDTO,
                position: Int,
                size: Int
            ) {

                Glide.with(holder.itemView)
                    .load(data.image)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                    .into(holder.imageView)
            }
        })
        bannerView?.addBannerLifecycleObserver(this)
        bannerView?.indicator = CircleIndicator(activity)

        bannerView?.setPageTransformer(DepthPageTransformer())

        bannerView?.setOnBannerListener { data, position ->
            run {

                startActivity(Intent(activity, VideoPlayActivity::class.java))
            }
        }

    }

}