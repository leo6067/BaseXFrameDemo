package com.xy.xframework.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.airbnb.lottie.utils.Utils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.notification.BaseNotificationItem;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.xy.xframework.R;
import com.xy.xframework.base.XBaseApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author: Leo
 * createDate: 2022/12/5 13:52
 */
public class DownLoadUtil {


    BaseDownloadTask singleTask;
    public int singleTaskId = 0;
    String apkUrl = "http://cdn.llsapp.com/android/LLS-v4.0-595-20160908-143200.apk";

    //文件目录
    public String mSinglePath;

    //APP 数据保存目录        文件目录  是app 缓存目录的子目录
    public String mSaveFolder = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "app_cache";


    public DownLoadUtil(String cachePath) {
        mSinglePath = cachePath + File.separator;
        mSaveFolder = cachePath;
    }


    public void singleDown(String downUrl, DownStateInter downStateInter) {
        String filePath = mSinglePath; //文件保存的目录，，app内部存储
        singleTask = FileDownloader.getImpl().create(downUrl)
//                .setPath(mSinglePath,false)
                .setPath(filePath, true)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                //.setTag()
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d("feifei", "progress taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes + ",speed:" + task.getSpeed());
                        if (downStateInter != null) {
                            downStateInter.downing(soFarBytes, totalBytes);
                        }
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.d("feifei", "blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() + ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
                        if (downStateInter != null) {
                            downStateInter.downSuccess(task.getFilename());
                        }
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.d("feifei", "completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                        Log.d("feifei", "paused taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.d("feifei", "error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                        if (downStateInter != null) {
                            downStateInter.downFail();
                        }
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.d("feifei", "warn taskId:" + task.getId());
                    }
                });

        singleTaskId = singleTask.start();
    }


    public void singlePause() {
        Log.d("feifei", "pause_single task:" + singleTaskId);
        FileDownloader.getImpl().pause(singleTaskId);
    }

    public void singleDelete() {
        //删除单个任务的database记录
        boolean deleteData = FileDownloader.getImpl().clear(singleTaskId, mSaveFolder);
        File targetFile = new File(mSinglePath);
        boolean delate = false;
        if (targetFile.exists()) {
            delate = targetFile.delete();
        }
        new File(FileDownloadUtils.getTempPath(mSinglePath)).delete();
    }


    // 多任务下载
    private FileDownloadListener downloadListener;

    public FileDownloadListener createLis() {
        return new FileDownloadSampleListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "pending taskId:" + task.getId() + ",fileName:" + task.getFilename() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "progress taskId:" + task.getId() + ",fileName:" + task.getFilename() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes + ",speed:" + task.getSpeed());
            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() + ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "paused taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "warn taskId:" + task.getId());
            }
        };
    }

    public void startMulti(String[] downUrl) {

        downloadListener = createLis();
        //(1) 创建 FileDownloadQueueSet
        final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(downloadListener);

        //(2) 创建Task 队列
        final List<BaseDownloadTask> tasks = new ArrayList<>();

        for (int i = 0; i < downUrl.length; i++) {
            BaseDownloadTask task1 = FileDownloader.getImpl().create(downUrl[i]).setPath(mSaveFolder, true);
            tasks.add(task1);
        }

        //(3) 设置参数

        // 每个任务的进度 无回调
        //queueSet.disableCallbackProgressTimes();
        // do not want each task's download progress's callback,we just consider which task will completed.

        queueSet.setCallbackProgressTimes(100);
        queueSet.setCallbackProgressMinInterval(100);
        //失败 重试次数
        queueSet.setAutoRetryTimes(3);

        //避免掉帧
        FileDownloader.enableAvoidDropFrame();

        //(4)串行下载
        queueSet.downloadSequentially(tasks);

        //(5)任务启动
        queueSet.start();
    }

    public void stopMulti() {
        FileDownloader.getImpl().pause(downloadListener);
    }

    public void deleteAllFile() {
        //清除所有的下载任务
        FileDownloader.getImpl().clearAllTaskData();
        //清除所有下载的文件
        int count = 0;
        File file = new File(FileDownloadUtils.getDefaultSaveRootPath());
        do {
            if (!file.exists()) {
                break;
            }

            if (!file.isDirectory()) {
                break;
            }

            File[] files = file.listFiles();

            if (files == null) {
                break;
            }

            for (File file1 : files) {
                count++;
                file1.delete();
            }

        } while (false);
    }


    public interface DownStateInter {

        void downing(int curr, int total);

        void downFail();

        void downSuccess(String fileName);


    }


    public static class NotificationItem extends BaseNotificationItem {

        private final NotificationCompat.Builder builder;

        private NotificationItem(int id, String title, String desc, String channelId) {
            super(id, title, desc);

            //设置显示Notification 以及点击跳转
//            final Intent[] intents = new Intent[2];
//            intents[0] = Intent.makeMainActivity(
//                    new ComponentName(DemoApplication.CONTEXT, MainActivity.class));
//            intents[1] = new Intent(XBaseApplication.application, NotificationSampleActivity.class);
//            final PendingIntent pendingIntent = PendingIntent.getActivities(
//                    XBaseApplication.application, 0, intents,
//                    PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder = new NotificationCompat.Builder(
                        FileDownloadHelper.getAppContext(),
                        channelId);
            } else {
                //noinspection deprecation
                builder = new NotificationCompat.Builder(FileDownloadHelper.getAppContext())
                        .setDefaults(Notification.DEFAULT_LIGHTS)
                        .setPriority(NotificationCompat.PRIORITY_MIN);
            }

//            builder.setContentTitle(getTitle())
//                    .setContentText(desc)
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.mipmap.ic_launcher);
        }

        @Override
        public void show(boolean statusChanged, int status, boolean isShowProgress) {
            String desc = "";
            switch (status) {
                case FileDownloadStatus.pending:
                    desc += " pending";
                    builder.setProgress(getTotal(), getSofar(), true);
                    break;
                case FileDownloadStatus.started:
                    desc += " started";
                    builder.setProgress(getTotal(), getSofar(), true);
                    break;
                case FileDownloadStatus.progress:
                    desc += " progress";
                    builder.setProgress(getTotal(), getSofar(), getTotal() <= 0);
                    break;
                case FileDownloadStatus.retry:
                    desc += " retry";
                    builder.setProgress(getTotal(), getSofar(), true);
                    break;
                case FileDownloadStatus.error:
                    desc += " error";
                    builder.setProgress(getTotal(), getSofar(), false);
                    break;
                case FileDownloadStatus.paused:
                    desc += " paused";
                    builder.setProgress(getTotal(), getSofar(), false);
                    break;
                case FileDownloadStatus.completed:
                    desc += " completed";
                    builder.setProgress(getTotal(), getSofar(), false);
                    break;
                case FileDownloadStatus.warn:
                    desc += " warn";
                    builder.setProgress(0, 0, true);
                    break;
            }

            builder.setContentTitle(getTitle()).setContentText(desc);
            getManager().notify(getId(), builder.build());
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        notificationHelper.clear();
//        Utils.deleteNotificationChannel(channelId, getApplicationContext());
//    }
}
