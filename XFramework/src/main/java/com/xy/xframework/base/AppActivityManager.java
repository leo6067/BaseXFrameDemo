package com.xy.xframework.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import com.xy.xframework.utils.Globals;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class AppActivityManager implements Application.ActivityLifecycleCallbacks{
    private int appCount = 0;

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     */
    private static class SingletonHolder {
        private static AppActivityManager instance = new AppActivityManager();
    }

    /**
     * 私有的构造函数
     */
    private AppActivityManager() {}

    public static AppActivityManager getInstance() {
        return SingletonHolder.instance;
    }

    // Activity栈
    private static Stack<Activity> activityStack;

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            // 退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0);
            // 从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        appCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        appCount--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        finishActivity(activity);

    }

    //获取在栈内的activity数量
    public int getAppCount() {
        return appCount;
    }




    /**
     * activity栈的最顶  判断当前界面是否在栈顶即 当前显示
     *
     * @return ActivityUtils.topActivity(this, LoginActivity.class.getName ());
     */
    public boolean topActivity(Context mContext, String activityName) {
        android.app.ActivityManager activityManager = (android.app.ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<android.app.ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        String packagename = "";
        if (tasksInfo == null || tasksInfo.size() == 0) {
            return false;
        } else {
            packagename = tasksInfo.get(0).topActivity.getClassName();
            if (activityName.equals(packagename)) {
                return true;
            }
        }
        return false;
    }




}
