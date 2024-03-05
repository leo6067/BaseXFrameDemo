package com.xy.demo.ui.cast

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import com.connectsdk.core.MediaInfo
import com.connectsdk.core.MediaInfo.Builder
import com.connectsdk.device.ConnectableDevice
import com.connectsdk.device.ConnectableDeviceListener
import com.connectsdk.discovery.DiscoveryManager
import com.connectsdk.discovery.DiscoveryManagerListener
import com.connectsdk.service.DeviceService
import com.connectsdk.service.DeviceService.PairingType
import com.connectsdk.service.capability.MediaControl
import com.connectsdk.service.capability.MediaPlayer
import com.connectsdk.service.capability.MediaPlayer.MediaLaunchObject
import com.connectsdk.service.capability.TVControl
import com.connectsdk.service.capability.VolumeControl
import com.connectsdk.service.command.ServiceCommandError
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.base.MyApplication
import com.xy.demo.databinding.ActivitySearchWifiBinding
import com.xy.demo.logic.file.FileManager
import com.xy.demo.model.MusicModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.adapter.WifiDeviceAdapter
import com.xy.demo.ui.dialog.MusicDialog
import com.xy.network.watch.NetworkStateLiveData
import com.xy.network.watch.NetworkType
import com.xy.xframework.imagePicker.RedBookPresenter
import com.xy.xframework.utils.ToastUtils
import com.yalantis.ucrop.util.FileUtils
import com.ypx.imagepicker.ImagePicker
import com.ypx.imagepicker.bean.ImageItem
import com.ypx.imagepicker.bean.MimeType
import com.ypx.imagepicker.data.OnImagePickCompleteListener


class SearchWifiActivity : MBBaseActivity<ActivitySearchWifiBinding, MBBaseViewModel>(), DiscoveryManagerListener {
	private var mDiscoveryManager: DiscoveryManager? = MyApplication.mDiscoveryManager
	
	var mConnectableDevice: ConnectableDevice? = null
	var mService: DeviceService? = null
	
	
	var mAdapter = WifiDeviceAdapter()
	
	
	lateinit var fileFormat: Set<MimeType>  //文件格式
	lateinit var fileTypeStr: String
	lateinit var otherFilePath: String
	
	
	//wifi
	override fun onDeviceAdded(manager: DiscoveryManager?, device: ConnectableDevice) {

//		for (service in device.services) {
//			Globals.log("xxxxxxadapter count---port-- service-" + service.serviceDescription.friendlyName)
//			Globals.log("xxxxxxadapter count---port-- service-" + service.serviceDescription.ipAddress)
//			Globals.log("xxxxxxadapter count---port-- service-" + service.serviceDescription.serviceID)
//			Globals.log("xxxxxxadapter count---port-- service-" + service.serviceDescription.port)
//		}
		
		mAdapter.addData(device)
		binding.searchLin.visibility = View.GONE
		binding.deviceLin.visibility = View.VISIBLE
	}
	
	override fun onDeviceUpdated(manager: DiscoveryManager, device: ConnectableDevice) {
		Globals.log("xxxxxxadapter onDeviceUpdated------" + device.toString())
		mAdapter.addData(device)
		
		binding.searchLin.visibility = View.GONE
		binding.deviceLin.visibility = View.VISIBLE
	}
	
	override fun onDeviceRemoved(manager: DiscoveryManager, device: ConnectableDevice) {
	
	}
	
	override fun onDiscoveryFailed(manager: DiscoveryManager, error: ServiceCommandError) {
	
	}
	
	
	//设备
	private val deviceListener: ConnectableDeviceListener = object : ConnectableDeviceListener {
		override fun onPairingRequired(device: ConnectableDevice, service: DeviceService, pairingType: PairingType) {
			Log.d("xxxxxx2ndScreenAPP", "Connected to " + mConnectableDevice?.getIpAddress())
			mService = service
			when (pairingType) {
				PairingType.FIRST_SCREEN -> {
					Log.d("xxxxx2ndScreenAPP", "First Screen")
//					pairingAlertDialog.show()
				}
				
				PairingType.PIN_CODE, PairingType.MIXED -> {
					Log.d("xxxxx2ndScreenAPP", "Pin Code")
				}
				
				PairingType.NONE -> {}
				else -> {}
			}
		}
		
		override fun onConnectionFailed(device: ConnectableDevice, error: ServiceCommandError) {
			Log.d("xxxxx2ndScreenAPP", "onConnectFailed")
			connectFailed(device)
			
		}
		
		override fun onDeviceReady(device: ConnectableDevice) {
			Log.d("xxxxxxx2ndScreenAPP", "onPairingSuccess")
			var mediaPlayer = device.getCapability<MediaPlayer>(MediaPlayer::class.java)
			var mediaControl = device.getCapability<MediaControl>(MediaControl::class.java)
			var tvControl = device.getCapability<TVControl>(TVControl::class.java)
			var volumeControl = device.getCapability<VolumeControl>(VolumeControl::class.java)
			
		}
		
		override fun onDeviceDisconnected(device: ConnectableDevice) {
			Log.d("xxxxxx2ndScreenAPP", "Device Disconnected")
			
		}
		
		override fun onCapabilityUpdated(device: ConnectableDevice, added: List<String>, removed: List<String>) {}
	}
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_search_wifi
	}
	
	override fun initView() {
		super.initView()
		notNetWorkLin = binding.netInclude.netLin
		
		this.mRecyclerView = binding.wifiListView
		initRecycler(1, 1, 1)
		mRecyclerView?.adapter = mAdapter
	}
	
	
	override fun onResume() {
		super.onResume()
		NetworkStateLiveData.observe(this) {
			if (it != NetworkType.WIFI) {
				ToastUtils.showShort(getString(R.string.please_use_wifi))
			}
		}
	}
	
	
	override fun initParams() {
		super.initParams()
		
		fileTypeStr = intent.getStringExtra(Constants.KEY_FILE_TYPE).toString()
		//wifi 搜索 监听回调
		mDiscoveryManager?.addListener(this)
		MyApplication.mDiscoveryManager.start()

//		try {
//			// DLNA
//			mDiscoveryManager?.registerDeviceService((Class.forName("com.connectsdk.service.DLNAService") as Class<DeviceService>),
//				(Class.forName("com.connectsdk.discovery.provider.SSDPDiscoveryProvider") as Class<DiscoveryProvider>))
//		} catch ( e :ClassNotFoundException) {
//			e.printStackTrace();
//		}
		
		
		val requestDataLauncher = registerForActivityResult(object : ActivityResultContract<String, String>() {
			override fun createIntent(context: Context, input: String?): Intent {
				//创建启动页面所需的Intent对象，传入需要传递的参数
				
				val intent = Intent(Intent.ACTION_GET_CONTENT)
				intent.setType("audio/mp4a-latm|audio/mpeg|video/mp4|audio/x-wav");
				intent.addCategory(Intent.CATEGORY_OPENABLE)
				
				return Intent.createChooser(intent, "请选择一个要上传的文件")
			}
			
			override fun parseResult(resultCode: Int, intent: Intent?): String {
				//页面回传的数据解析，相当于原onActivityResult方法
				
				try {
					otherFilePath = FileUtils.getPath(this@SearchWifiActivity, intent?.data)
				} catch (_: Exception) {
				
				}
				Globals.log("xxxxxx otherFilePath data000000" + otherFilePath)
				return if (resultCode == RESULT_OK) "one" else ""
			}
		}) {
			Globals.log("xxxxxx otherFilePath data" + otherFilePath)
		}


//		when (fileTypeStr) {
//			"image/jpeg" -> {
//				fileFormat = MimeType.ofImage()
//				chooseImage()
//			}
//
//			"video/mp4" -> {
//				fileFormat = MimeType.ofVideo()
//				chooseImage()
//			}
//
//			"audio/x-wav" -> {
//				//常规带回调启动Activity
//				requestDataLauncher.launch("SearchWifiActivity")
//			}
//		}


//
		
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			mConnectableDevice = mAdapter.getItem(position)
			mConnectableDevice?.addListener(deviceListener)
			mConnectableDevice?.setPairingType(null)
			mConnectableDevice?.connect()
//			connectItem.setTitle(mTV.getFriendlyName())

//			sendMediaFile("https://media.w3.org/2010/05/sintel/trailer.mp4", "video/mp4")
			
			
			when (fileTypeStr) {
				"image/jpeg" -> {
					fileFormat = MimeType.ofImage()
					chooseImage()
				}
				
				"video/mp4" -> {
					fileFormat = MimeType.ofVideo()
					chooseImage()
				}
				
				"audio/x-wav" -> {
					//常规带回调启动Activity
//					requestDataLauncher.launch("SearchWifiActivity")
					XXPermissions.with(this)
						.permission(Permission.CAMERA)
						.request { permissions, all ->
							val musicDialog = MusicDialog()
							musicDialog.show(supportFragmentManager, "")
							musicDialog.musicAdapter.setOnItemClickListener { adapter, view, position ->
								val musicModel = adapter.data[position] as MusicModel
								Globals.log("xxxxxxxmusicAdapter" + musicModel.toString())
								MyApplication.httpService.setFilePath(musicModel.path, "video/mp4")
								sendMediaFile(fileTypeStr,musicModel.name)
							}
						}
				}
			}
		}
		
	}
	
	
	fun chooseImage() {
		XXPermissions.with(this)
			.permission(Permission.CAMERA)
			.request { permissions, all ->
				ImagePicker.withMulti(RedBookPresenter()) //设置presenter
					.setMaxCount(1) //设置选择数量
					.setColumnCount(4) //设置列数
					.mimeTypes(fileFormat)
					//设置需要过滤掉加载的文件类型
					.setPreview(true)
					.setVideoSinglePick(true) //设置视频单选
					.setMaxVideoDuration(200000000L) //设置可选区的最大视频时长
					.pick(this, OnImagePickCompleteListener {
						if (it.size == 0) {
							return@OnImagePickCompleteListener
						}
						val imageItem: ImageItem = it.get(0)
						if (imageItem.uri != null) {
							val imgPath = FileUtils.getPath(this, imageItem.uri)
							
							Globals.log("App Tag" + "Display photo String:  imgPath $imgPath")
							MyApplication.httpService.setFilePath(imgPath, fileTypeStr)
							
							sendMediaFile(fileTypeStr)
						} else {
							ToastUtils.showShort("open failure")
						}
					})
			}
		
	}
	
	
	fun sendMediaFile(mimeType: String,title:String ="") {
		
		
		showLoading()
		var mediaUrl = "http://" + getLocalIpStr(this) + ":" + Constants.NANO_SORT + "/" + System.currentTimeMillis()
		var iconUrl = "http://" + getLocalIpStr(this) + ":" + Constants.NANO_SORT + "/loadingpng"
		
		Globals.log("App Tag" + "Display photo String:  mediaUrl $mediaUrl")
 
		val description = "screen"
		
		val mediaInfo: MediaInfo = Builder(mediaUrl, mimeType)
			.setTitle(title)
			.setDescription(description)
			.build()
		
		
		val listener: MediaPlayer.LaunchListener = object : MediaPlayer.LaunchListener {
			override fun onSuccess(mediaLaunchObject: MediaLaunchObject) {
				// save these object references to control media playback
//					mLaunchSession = mediaLaunchObject.launchSession
//					mMediaControl = mediaLaunchObject.mediaControl
				
				// you will want to enable your media control UI elements here
				dismissLoading()
				
				Globals.log("App Tag" + "Display photo mediaLaunchObject" + mediaLaunchObject.mediaControl.toString())
			}
			
			override fun onError(error: ServiceCommandError) {
				dismissLoading()
				Globals.log("App Tag" + "Display photo failure: $error")
			}
		}
		
		mConnectableDevice?.getMediaPlayer()?.playMedia(mediaInfo, false, listener)
		
	}
	
	
	override fun onDestroy() {
		super.onDestroy()
		Globals.log("App Tag" + "Display photo failure: onDestroy")
	}
	
	fun connectFailed(device: ConnectableDevice) {
		Globals.log("App Tag" + "Display photo failure: connectFailed")
//		device.removeListener(deviceListener)
//		device.disconnect()
		
	}
	
	
	fun getLocalIpStr(context: Context): String? {
		val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
		val wifiInfo = wifiManager.connectionInfo
		return intToIpAddr(wifiInfo.ipAddress)
	}
	
	private fun intToIpAddr(ip: Int): String? {
		return (ip and 0xff).toString() + "." + (ip shr 8 and 0xff) + "." + (ip shr 16 and 0xff) + "." + (ip shr 24 and 0xff)
	}
	
	
	override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
		return super.onKeyDown(keyCode, event)
	}


//	private static void tryRecycleAnimationDrawable(AnimationDrawable animationDrawable) {
//		if (animationDrawable != null) {
//			animationDrawable.stop();
//			for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
//				Drawable frame = animationDrawable.getFrame(i);
//				if (frame instanceof BitmapDrawable) {
//					((BitmapDrawable) frame).getBitmap().recycle();
//				}
//				frame.setCallback(null);
//			}
//			animationDrawable.setCallback(null);
//		}
//	}


}