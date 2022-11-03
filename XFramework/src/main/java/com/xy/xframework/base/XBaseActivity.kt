package com.xy.xframework.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xy.xframework.R
import com.xy.xframework.dialog.loading.LoadingDialogProvider
import com.xy.xframework.statusBar.StatusBarUtil
import com.xy.xframework.swipeback.SwipeBackActivityHelper
import com.xy.xframework.swipeback.SwipeBackLayout
import com.xy.xframework.titlebar.GlobalTitleBarProvider
import com.xy.xframework.titlebar.TitleBarView
import java.lang.reflect.ParameterizedType

abstract class XBaseActivity<T : ViewDataBinding, VM : XBaseViewModel> : AppCompatActivity() {
    lateinit var binding: T

    protected lateinit var viewModel: VM

    abstract fun getLayoutId(): Int

    abstract fun initVariableId(): Int

    private var viewModelId: Int = 0

    private val titleBarBuilder = GlobalTitleBarProvider.getTitleBarBuilder()

    var titleBarView: TitleBarView? = null

    private var mHelper: SwipeBackActivityHelper? = null
    private var mSwipeBackLayout: SwipeBackLayout? = null
    private var dialog: Dialog? = null

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (forcePortrait()) {
            try {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } catch (e: Exception) {
            }
        }
        super.onCreate(savedInstanceState)
        initParams()
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        addTitleBar(showTitleBar())
        initViewModel()
        binding.setVariable(viewModelId, viewModel)
        binding.lifecycleOwner = this

        if (fitsSystemWindows()) {
            StatusBarUtil.setStatusBarColor(this, titleBarBuilder.getStatusBarColor())
        } else {
            StatusBarUtil.setStatusBarColor(this, Color.TRANSPARENT)
            StatusBarUtil.setFitsSystemWindows(this, fitsSystemWindows())
        }
        initSwipeBackLayout()
        registerUIChangeEventCallBack()
        initBase()
        initView()
        initViewObservable()
    }

    open fun initBase() {

    }

    /**
     * 初始化参数
     */
    open fun initParams() {

    }

    /**
     * 处理ui通知，如liveDate
     */
    open fun initViewObservable() {

    }

    /**
     * 处理viewModel事件
     */
    private fun registerUIChangeEventCallBack() {

        viewModel.mUiEvent.showDialogEvent.observe(this, Observer {
            showLoading(it.title, it.isCancelable, it.isCancelOutside, it.onCancelListener)
        })

        viewModel.mUiEvent.dismissDialogEvent.observe(this) { dismissLoading() }
        viewModel.mUiEvent.finishEvent.observe(this) { finish() }

        viewModel.mUiEvent.onBackPressedEvent.observe(this) { onBackPressed() }
    }

    /**
     * 关闭加载对话框
     * <p>
     * Author: zhuanghongzhan
     * Date: 2020-12-24
     */
    open fun dismissLoading() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    /**
     * 显示加载对话框
     * @param title String? 加载标题
     * @param isCancelable Boolean 是否可以关闭
     * @param isCancelOutside Boolean 是否可以点击外部管不
     * @param onCancelListener OnCancelListener? dialog关闭监听
     */
    open fun showLoading(
        title: String? = "",
        isCancelable: Boolean = true,
        isCancelOutside: Boolean = false,
        onCancelListener: DialogInterface.OnCancelListener? = null
    ) {
        if (!isDestroyed) {
            if (dialog == null) {
                dialog = LoadingDialogProvider.createLoadingDialog(this, title)
            }
            dialog?.setCancelable(isCancelable)
            dialog?.setCanceledOnTouchOutside(isCancelOutside)
            dialog?.setOnCancelListener(onCancelListener)
            dialog?.show()
        }
    }

    /**
     * 设置状态栏颜色
     */
    open fun setStatusBarColor(activity: Activity, colorId: Int) {
        StatusBarUtil.setStatusBarColor(activity, colorId)
    }

    /**
     * 初始化左滑关闭
     */
    open fun initSwipeBackLayout() {
        if (!isSwipeBackClose()) {
            mHelper = SwipeBackActivityHelper(this)
            mHelper?.onActivityCreate()
            mSwipeBackLayout = mHelper?.swipeBackLayout
            mSwipeBackLayout?.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        }
    }

    open fun isSwipeBackClose(): Boolean = false

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper?.onPostCreate()
    }

    /**
     * 添加通用标题栏
     */
    @SuppressLint("InflateParams")
    private fun addTitleBar(showTitleBar: Boolean) {
        if (showTitleBar) {
            val contentView = findViewById<ViewGroup>(android.R.id.content)
            if (contentView.childCount > 0) {
                val view =
                    LayoutInflater.from(this).inflate(R.layout.root_layout, null) as LinearLayout
                titleBarView = view.findViewById(R.id.titleBarView)
                val contain = contentView[0]
                contentView.removeView(contain)
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                view.addView(contain, params)
                val containParams = contain.layoutParams
                contentView.addView(view, containParams)
                initToolBar()
            }
        }
    }

    private fun initToolBar() {
        titleBarView?.setTitleBarBuilder(titleBarBuilder)
        titleBarView?.setLeftClickListener { onLeftClick() }

        titleBarView?.tvTitle?.setOnClickListener {
            //如果设置点击标题可返回，则生效
            if (titleBarBuilder.clickTitleToBack) {
                onLeftClick()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }

    /**
     * 左边按钮点击事件，默认是关闭页面
     */
    open fun onLeftClick() {
        onBackPressed()
    }

    /**
     * 是否强制竖屏
     */
    open fun forcePortrait(): Boolean {
        return true
    }

    /**
     * 是沉浸式  true:不沉浸式 false：沉浸式
     */
    open fun fitsSystemWindows(): Boolean {
        return true
    }

    open fun showTitleBar(): Boolean {
        return true
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        viewModelId = initVariableId()
        val viewModelType = getViewModelType()
        viewModel = createViewModel(this, viewModelType as Class<ViewModel>) as VM
    }

    protected open fun <T : ViewModel> createViewModel(
        activity: FragmentActivity,
        cls: Class<T>
    ): T {
        return ViewModelProvider(
            activity,
            AndroidViewModelFactory(application, ::createViewModel)
        )[cls]
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun createViewModel(): VM {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(getViewModelType() as Class<VM>)
    }

    open fun getViewModelType(): Class<*> {
        val modelClass: Class<*>
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<*>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            XBaseViewModel::class.java
        }
        return modelClass
    }
}