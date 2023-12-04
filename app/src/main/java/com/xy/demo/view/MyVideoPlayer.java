package com.xy.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xy.demo.R;

import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;

/**
 * author: Leo
 * createDate: 2023/12/1 15:27
 */
public class MyVideoPlayer extends StandardGSYVideoPlayer {


    public MyVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyVideoPlayer(Context context) {
        super(context);
    }

    public MyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static void initVideoManager() {

        PlayerFactory.setPlayManager(Exo2PlayerManager.class);//SCREEN_MATCH_FULL
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_DEFAULT);
        // CacheFactory.setCacheManager(new ExoPlayerCacheManager());//exo缓存模式，支持m3u8，只支持exo
    }



    @Override
    protected void init(Context context) {
        super.init(context);
//        findViewById()
    }





    @Override
    public int getLayoutId() {
        return R.layout.view_my_video;
    }


    //重载空  去掉双击功能
    @Override
    protected void touchDoubleUp(MotionEvent e) {
//        super.touchDoubleUp(e);
    }


    @Override
    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
//        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
    }
}
