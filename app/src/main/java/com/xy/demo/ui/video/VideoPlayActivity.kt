package com.xy.demo.ui.video

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityVideoPlayBinding
import com.xy.demo.ui.video.adapter.VideoAdapter
import com.xy.demo.view.MyVideoPlayer

class VideoPlayActivity : MBBaseActivity<ActivityVideoPlayBinding,MBBaseViewModel>() {


    override fun getLayoutId(): Int {
       return R.layout.activity_video_play
    }


    override fun initView() {
        super.initView()
        MyVideoPlayer.initVideoManager()



       var viewPagerAdapter = VideoAdapter( )


        val arrayOf = ArrayList<String>()
        arrayOf.add("ssssss")
        arrayOf.add("666")
        arrayOf.add("333")
        arrayOf.add("545")
        arrayOf.add("56464")

        viewPagerAdapter.setNewInstance(arrayOf)
        binding.viewpager.setOrientation(ViewPager2.ORIENTATION_VERTICAL)
        binding.viewpager.setAdapter(viewPagerAdapter)
        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //大于0说明有播放
                val playPosition = GSYVideoManager.instance().playPosition
                if (playPosition >= 0) {
                    //对应的播放列表TAG
//                    if (GSYVideoManager.instance().playTag == RecyclerItemNormalHolder.TAG && position != playPosition) {
//                        GSYVideoManager.releaseAllVideos()
//                        playPosition(position)
//                    }
                }
            }
        })


        binding.viewpager.post(Runnable { playPosition(0) })

    }

    private fun playPosition(position: Int) {
        binding.viewpager.postDelayed(Runnable {
            val viewHolder =
                (binding.viewpager.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(position)
//            viewHolder.
//            if (viewHolder != null) {
//                val recyclerItemNormalHolder: RecyclerItemNormalHolder =
//                    viewHolder as RecyclerItemNormalHolder
//                recyclerItemNormalHolder.getPlayer().startPlayLogic()
//            }
        }, 50)
    }
}