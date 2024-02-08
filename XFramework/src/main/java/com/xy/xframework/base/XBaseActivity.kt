package com.xy.xframework.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.NetworkUtils
import com.xy.network.watch.NetworkStateLiveData
import com.xy.network.watch.NetworkType
import com.xy.xframework.R
import com.xy.xframework.dialog.loading.LoadingDialogProvider

import com.xy.xframework.statusBar.StatusBarUtil
import com.xy.xframework.swipeback.SwipeBackActivityHelper
import com.xy.xframework.swipeback.SwipeBackLayout
import com.xy.xframework.titlebar.GlobalTitleBarProvider
import com.xy.xframework.titlebar.TitleBarView
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.ToastUtils
import com.xy.xframework.view.ViewUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
	var dialog: Dialog? = null
	
	
	var mRecyclerView: RecyclerView? = null
	var notNetWorkLin: View? = null
 
	
	abstract fun initView()
	
	@RequiresApi(Build.VERSION_CODES.Q)
	override fun onCreate(savedInstanceState: Bundle?) {
		if (forcePortrait()) {
			try {
				requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
			} catch (e: Exception) {
			}
		}
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, getLayoutId())
		initBase()
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
		initView()
		initParams()
		initViewObservable()
		
	}
	
	
	fun initRecycler(layManageType: Int, orientation: Int, gridNum: Int) {
		if (layManageType == 1) {
			if (orientation == 1) {
				mRecyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
			} else {
				mRecyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
			}
		} else {
			mRecyclerView?.layoutManager = GridLayoutManager(this, gridNum)
		}
	}
	
	
	open fun initBase() {
	
	}
	
	
	/**
	 * 初始化参数
	 */
	open fun initParams() {

//		var hasNetWork = NetworkUtils.isConnected()
//
//		NetworkStateLiveData.observe(this) {
//			Globals.log("xxxxxxxnet  " + it)
//			if (it == NetworkType.CONNECT) {
//				ToastUtils.showShort("Network connected")
//				hasNetWork = true
//			}
//			if (it == NetworkType.NONE){
//				ToastUtils.showShort("Network disconnected")
//				hasNetWork = false
//			}
//		}
//
//
//		if (hasNetWork) {
//			if (notNetWorkLin != null) {
//				notNetWorkLin?.visibility = View.GONE
//			}
//		} else {
//			if (notNetWorkLin != null) {
//				notNetWorkLin?.visibility = View.VISIBLE
//			}
//		}
	}
	
	open fun onClick(view: View) {
		finish()
	}
	
	/**
	 * 处理ui通知，如liveDate
	 */
	open fun initViewObservable() {
	
	}
	
	override fun onResume() {
		super.onResume()
		Globals.log("xxxxxxxonResume")
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
			
			GlobalScope.launch {
				delay(4500)
				if (dialog != null && dialog!!.isShowing) {
					dialog!!.dismiss()
					dialog = null
				}
			}
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
	
	open fun isSwipeBackClose(): Boolean = true
	
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
	
	override fun onStop() {
		super.onStop()
		
	 
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
	
	override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
		if (ev.action == MotionEvent.ACTION_DOWN) {
			// 判断连续点击事件时间差
			if (ViewUtil.isFastClick()) {
				return true
			}
		}
		return super.dispatchTouchEvent(ev)
	}
	
}