package com.xy.xframework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.qw.download.entities.DownloadFile;
import com.qw.download.manager.FileRequest;
import com.ruffian.library.widget.RTextView;


import java.io.File;
import java.util.Formatter;


// leo ： 集成 断点下载， 安装 ，进度条

public class TextProgressBar extends FrameLayout implements View.OnClickListener {

//    private RTextView mDownloadStatus;
//    private ProgressBar mDownloadProgress;
//
//
//    private GameApkBean mApkBean;
//    private onProgressListener mOnProgressListener;
//
    public TextProgressBar(Context context) {
        super(context);
//        initView();
    }

    public TextProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        initView();
    }

    public TextProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initView();
    }

    @Override
    public void onClick(View view) {

    }
//
//
//    //初始化View
//    private void initView() {
//        inflate(getContext(), R.layout.layout_view_progress, this);
//        mDownloadProgress = findViewById(R.id.down_status_progress);
//        mDownloadStatus = findViewById(R.id.down_status_tv);
//        mDownloadStatus.setOnClickListener(this);
//    }
//
//
//    public void setOnProgressListener(onProgressListener onProgressListener) {
//        mOnProgressListener = onProgressListener;
//    }
//
//
//
//    public void initializeData(GameApkBean gameApkBean) {
//        mApkBean = gameApkBean;
//        DownloadFile file = FileRequest.getFile(mApkBean.getId());
//        boolean checkApkInstalled = ApkUtils.hasApkInstalled(getContext(), mApkBean.getId());
//
//        mDownloadStatus.getHelper().setBackgroundColorNormal(getContext().getResources().getColor(R.color.common_color_0086FF));
//        mDownloadStatus.getHelper().setTextColorNormal(getContext().getResources().getColor(R.color.white));
//        mDownloadStatus.getHelper().setBorderColorNormal(getContext().getResources().getColor(R.color.common_color_0086FF));
//
//        if (checkApkInstalled) { //已安装  显示启动
//            mApkBean.status = 6;
//            mDownloadStatus.setText("启动");
//            return;
//        }
//
//
//        // 预约
//        if (mApkBean.status == 1) {
//            mDownloadStatus.setText("预约");
//
//        } else if (mApkBean.status == 2) {
//            mDownloadStatus.setText("已预约");
//            mDownloadStatus.getHelper().setBackgroundColorNormal(getContext().getResources().getColor(R.color.common_color_5CB2FF));
//            mDownloadStatus.getHelper().setTextColorNormal(getContext().getResources().getColor(R.color.white));
//            mDownloadStatus.getHelper().setBorderColorNormal(getContext().getResources().getColor(R.color.transparent));
//        } else if (mApkBean.status == 3) {
//            mDownloadStatus.setText("预下载");
//        } else {
//
//            if (file == null || file.isIdle()) {
//                mDownloadStatus.setText("下载");
//                if (mOnProgressListener != null) {
//                    mOnProgressListener.onBusiness(ApkStatus.DOWNLOAD);
//                }
//
//            } else if (file.isPaused()) { //暂停下载
//                mDownloadStatus.getHelper().setBackgroundColorNormal(getContext().getResources().getColor(R.color.white));
//                mDownloadStatus.getHelper().setTextColorNormal(getContext().getResources().getColor(R.color.common_color_0086FF));
//                mDownloadStatus.setText("继续");
//                if (mOnProgressListener != null) {
//                    mOnProgressListener.onBusiness(ApkStatus.PAUSEDOWNLOAD);
//                }
//
//            } else if (file.isWait()) {
//                mDownloadStatus.setText("等待");
//            } else if (file.isConnecting() || file.isDownloading()) {  //下载中
//                float i = ((float) file.getCurrentLength() / (float) file.getContentLength());
//                mDownloadStatus.setText((int) (i * 100) + "%");
//                mDownloadStatus.getHelper().setBackgroundColorNormal(getContext().getResources().getColor(R.color.transparent));
//                mDownloadStatus.getHelper().setBorderColorNormal(getContext().getResources().getColor(R.color.transparent));
//                if (mOnProgressListener != null) {
//                    mOnProgressListener.onBusiness(ApkStatus.DOWNLOADING);
//                }
////                mDownloadStatus.setText("暂停");
//            } else if (file.isError()) {
//                mDownloadStatus.setText("重试");
//            } else if (file.isDone()) {
//                mDownloadStatus.setText("安装");
//                if (mOnProgressListener != null) {
//                    mOnProgressListener.onBusiness(ApkStatus.DOWNLOADED);
//                }
//            }
//        }
//
//
//        if (file != null) {
//            if (file.getContentLength() == 0L) {
//                mDownloadProgress.setProgress(0);
//            } else {
//                float i = ((float) file.getCurrentLength() / (float) file.getContentLength());
//                mDownloadProgress.setProgress((int) (i * 100));
//                if (mOnProgressListener != null) {
//                    mOnProgressListener.onProgressSpeed(formatSize(file.getSpeed()) + "/s");
//                }
//            }
//        }
//
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (mApkBean == null) {
//            return;
//        }
//        if (mApkBean.status == 1) {
//            if (mOnProgressListener != null) {
//                mOnProgressListener.onBusiness(ApkStatus.SUBSCRIBE);
//            }
//            return;
//        }
//        if (mApkBean.status == 2) {
//            if (mOnProgressListener != null) {
//                mOnProgressListener.onBusiness(ApkStatus.ALREADYSUBSCRIBE);
//            }
//            return;
//        }
//
//        if (mApkBean.status == 6) {
//            ApkUtils.openApp(v.getContext(), mApkBean.id);
//            return;
//        }
//
//        DownloadFile file = FileRequest.getFile(mApkBean.getId());
//        if (file == null || file.isIdle()) {
//            FileRequest.create(mApkBean.getId())
//                    .setName(mApkBean.name + ".apk")
//                    .setUrl(mApkBean.url)
//                    .addDownload();
//            SharePreferenceUtils.putValue(v.getContext(), mApkBean.getId() + AppConst.GameConst.GAME_ICON, mApkBean.cover);
//            SharePreferenceUtils.putValue(v.getContext(), mApkBean.getId() + AppConst.GameConst.GAME_DES, mApkBean.des);
//        } else if (file.isPaused() || file.isError()) {
//            FileRequest.create(mApkBean.getId()).resumeDownload();
//        } else if (file.isConnecting() || file.isDownloading() || file.isWait()) {
////            mDownloadStatus.setText("暂停");
//            FileRequest.create(mApkBean.getId()).pauseDownload();
//        } else if (file.isDone()) {
//            File apkFile = new File(file.getPath());
//            if (!apkFile.exists()) {
//                Toast.makeText(v.getContext(), "文件已被删除！", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            // 通过Intent安装APK文件
//            ApkUtils.loadInstallApk(v.getContext(), apkFile);
//        }
//    }
//
//
//    private String formatSize(long size) {
//        String unit = "";
//        String newSize = "0";
//        if (size >= 1024) {
//            unit = "KB";
//            size = (long) (size / 1024f);
//            newSize = size + "";
//            if (size >= 1024) {
//                unit = "MB";
//                newSize = new Formatter().format("%.1f", size / 1024f).toString();
//            }
//        }
//        return newSize + unit;
//    }
//
//
//    public interface onProgressListener {
//
//        void onProgressSpeed(String seedStr);
//
//        void onBusiness(ApkStatus status);
//    }
}

