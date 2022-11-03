package com.xy.xframework.base

import com.xy.xframework.command.SingleLiveEvent
import com.xy.xframework.dialog.loading.ShowDialogEntity

class UIChangeEvent {

    //显示加载对话框
    val showDialogEvent by lazy(LazyThreadSafetyMode.NONE) { SingleLiveEvent<ShowDialogEntity>() }

    //关闭加载对话框
    val dismissDialogEvent by lazy(LazyThreadSafetyMode.NONE) { SingleLiveEvent<Void>() }

    //关闭页面
    val finishEvent by lazy(LazyThreadSafetyMode.NONE) { SingleLiveEvent<Void>() }

    //返回事件
    val onBackPressedEvent by lazy(LazyThreadSafetyMode.NONE) { SingleLiveEvent<Any>() }

}