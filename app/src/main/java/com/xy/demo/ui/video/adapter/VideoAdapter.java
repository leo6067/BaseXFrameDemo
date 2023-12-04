package com.xy.demo.ui.video.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xy.demo.R;

/**
 * author: Leo
 * createDate: 2023/12/1 15:41
 */
public class VideoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public VideoAdapter() {
        super(R.layout.item_video_play);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();

    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String json) {


        StandardGSYVideoPlayer videoView = baseViewHolder.getView(R.id.videoView);
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                //.setThumbImageView(imageView)
                .setUrl("http://beiwo.oss-cn-beijing.aliyuncs.com/Video-data/338_1685524103.mp4")
                .setVideoTitle(json)
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
//                .setPlayTag("position"+getItemPosition(json))
//                .setMapHeadData(header)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
//                .setPlayPosition(position)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!videoView.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(true);
                        }

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        videoView.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                }).build(videoView);


    }
}
