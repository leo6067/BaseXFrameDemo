package com.xy.demo.adapter;

import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.AppUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.R;
import com.xy.demo.utils.MyUtils;
import com.xy.xframework.utils.DateUtils;
import com.xy.xframework.utils.PackageUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class AppAdapter extends BaseQuickAdapter<AppUtils.AppInfo, BaseViewHolder> {
    public AppAdapter() {
        super(R.layout.item_app_manage);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, AppUtils.AppInfo bean) {
        ImageView itemIcon = baseViewHolder.getView(R.id.itemIcon);
        TextView itemTitle = baseViewHolder.getView(R.id.itemTitle);
        TextView itemIns = baseViewHolder.getView(R.id.itemIns);
        TextView itemSize = baseViewHolder.getView(R.id.itemSize);
        TextView uninstallTV = baseViewHolder.getView(R.id.uninstallTV);

        itemTitle.setText(bean.getName());
        Glide.with(itemIcon.getContext()).load(bean.getIcon()).into(itemIcon);

        try{
            PackageInfo appInfo = PackageUtils.getInstance().getAppInfo(getContext(), bean.getPackageName());
            itemIns.setText(getContext().getString(R.string.installed) +  DateUtils.getTimeStampString(appInfo.lastUpdateTime,"yyyy-MM-dd",0));
            long apkSize = new File(appInfo.applicationInfo.sourceDir).length();
            itemSize.setText(getContext().getString(R.string.size_used) +  MyUtils.byte2FitMemorySize(apkSize));
        }catch (Exception e){
            itemIns.setText(getContext().getString(R.string.installed));
            itemSize.setText(getContext().getString(R.string.size_used));
        }

    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long getAppStorage(Context context, String packageName) {
        StorageStatsManager storageStatsManager = (StorageStatsManager) context.getSystemService(Context.STORAGE_STATS_SERVICE);
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        List<StorageVolume> storageVolumes = storageManager.getStorageVolumes();
        long appSizeL =0;
        for (StorageVolume storageVolume : storageVolumes) {
            UUID uuid = null;
            String uuidStr = storageVolume.getUuid();
            try {
                if (TextUtils.isEmpty(uuidStr)){
                    uuid = StorageManager.UUID_DEFAULT;
                }else {
                    uuid = UUID.fromString(uuidStr);
                }
            }catch (Exception e){
                uuid = StorageManager.UUID_DEFAULT;
            }

            StorageStats storageStats = null;
            try {
                UserManager um = (UserManager) context.getSystemService(Context.USER_SERVICE);
                UserHandle userHandle = um.getUserProfiles().get(0);
                storageStats = storageStatsManager.queryStatsForPackage(uuid, packageName, userHandle);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
            //获取到App的总大小
            appSizeL = storageStats.getAppBytes() + storageStats.getCacheBytes() + storageStats.getDataBytes();
        }
        return appSizeL;
    }


    public static int getUid(Context context, String pakName) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(pakName, PackageManager.GET_META_DATA);
            return ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static class AppSpaceInfo {
        /**
         * 设置-应用信息里的"应用"占用
         */
        public long apkBytes;

        /**
         * 设置-应用信息里的"数据"占用
         */
        public long dataBytes;

        /**
         * 设置-应用信息里的"缓存"占用
         */
        public long cacheByte;

        public String pacakgeName;


        AppSpaceInfo(long apkBytes, long dataBytes, long cacheByte){
            this.apkBytes = apkBytes;
            this.dataBytes = dataBytes;
            this.cacheByte = cacheByte;
        }

        @Override
        public String toString() {
            return "AppSpaceInfo{" +
                    "apkBytes=" + apkBytes +
                    ", dataBytes=" + dataBytes +
                    ", cacheByte=" + cacheByte +
                    ", pacakgeName='" + pacakgeName + '\'' +
                    '}';
        }
    }


}
