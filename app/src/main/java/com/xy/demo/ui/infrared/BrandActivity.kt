package com.xy.demo.ui.infrared

import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.alibaba.fastjson.JSONArray
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityBrandBinding
import com.xy.demo.db.RemoteModel
import com.xy.demo.logic.JsonUtil
import com.xy.demo.model.BrandListModel
import com.xy.demo.model.BrandModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.adapter.BrandAdapter
import com.xy.xframework.base.BaseSharePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Collections
import java.util.Locale
import java.util.regex.Pattern


//品牌 型号
class BrandActivity : MBBaseActivity<ActivityBrandBinding, MBBaseViewModel>() {
	
	
	var brandModelList = ArrayList<BrandModel>()
	var tempList = ArrayList<BrandModel>()
	
	val mAdapter = BrandAdapter()
	
	
	var remoteModel = RemoteModel()
	
	
	private var isFromFeedBack = false
	
	companion object {
		var activity: BrandActivity? = null
	}
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_brand
	}
	
	
	override fun initView() {
		super.initView()
		activity = this
		notNetWorkLin = binding.netInclude.netLin
		
		
		if (intent.getSerializableExtra(Constants.KEY_REMOTE) != null) {
			remoteModel = intent.getSerializableExtra(Constants.KEY_REMOTE) as RemoteModel
		}
		
		
		if (intent.getStringExtra(Constants.KEY_FEEDBACK) != null) {
			isFromFeedBack = true
		}
		
		
		binding.titleLay.titleTV.text = getString(R.string.select_tv_brand)
		binding.recyclerView.visibility = View.GONE
		
		lifecycleScope.launch {
			delay(1200)
			binding.shimmerLay.visibility = View.GONE
			binding.recyclerView.visibility = View.VISIBLE
		}
		
		val brandListStr = BaseSharePreference.instance.getString(Constants.KEY_BRAND_LIST, "")
		val brandListModel = JSONArray.parseObject(brandListStr, BrandListModel::class.java)

//		brandModelList = JSONArray.parseArray(JsonUtil.paramJson(this@BrandActivity,"BrandList.json"), BrandModel::class.java) as ArrayList<BrandModel>
		brandModelList = brandListModel.list as ArrayList<BrandModel>
		
		
		
		Collections.sort(brandModelList, Comparator<BrandModel> { o1, o2 -> //拿到2个bean类中的name字符串进行比较，android中字符串比较是比较的ASCLL码
			//compareTo（） 字符串比较
			val lhsStartsWithLetter = Character.isLetter(o1.brandName[0])
			val rhsStartsWithLetter = Character.isLetter(o2.brandName[0])
			if (lhsStartsWithLetter && rhsStartsWithLetter || !lhsStartsWithLetter && !rhsStartsWithLetter) {
				// they both start with letters or not-a-letters
				o1.brandName.compareTo(o2.brandName)
			} else if (lhsStartsWithLetter) {
				// the first string starts with letter and the second one is not
				-1
			} else {
				// the second string starts with letter and the first one is not
				1
			}
		})
		
		
		
		
		tempList = brandModelList
		var tipCaseA = "A"
		//先添加 一条 A
		var brandModel = BrandModel()
		brandModel.brandName = "A"
		brandModelList.add(0, brandModel)
		for (index in tempList.indices) {
			if (tempList[index].brandName.substring(0, 1).uppercase() != tipCaseA) {
				// 插入一条
				var brandModel = BrandModel()
				brandModel.brandName = tempList[index].brandName.substring(0, 1)
				tipCaseA = tempList[index].brandName.substring(0, 1)
				brandModelList.add(index, brandModel)
			}
		}
		
		
		
		this.mRecyclerView = binding.recyclerView
		initRecycler(1, 1, 1)
		binding.recyclerView.adapter = mAdapter
		mAdapter.setNewInstance(brandModelList)
		
		
		//排序滑动
		binding.sideBarLayout.setSideBarLayout {
			//it  开头关键词  根据自己业务实现    大小写 兼容
			for (position in 0 until brandModelList.size) {
				if (brandModelList[position].brandName.startsWith(it) || brandModelList[position].brandName.startsWith(it.lowercase(Locale.ROOT))) {
					moveToPosition(position)
					break
				} else if (brandModelList[position].brandName.substring(0, 1).matches(Regex("^[a-zA-Z]"))) {    //井
					moveToPosition(position)
				}
			}
		}
		
		// item 滑动
		mRecyclerView?.addOnScrollListener(object : OnScrollListener() {
			override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
				super.onScrollStateChanged(recyclerView, newState)
				if (newState == RecyclerView.SCROLL_STATE_IDLE) {    //滑动停止
					val linearLayoutManager = mRecyclerView?.layoutManager as LinearLayoutManager
					val firstItemPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()
					binding.sideBarLayout.onItemScrollUpdateSideBarText(
						brandModelList[firstItemPosition].brandName.substring(0, 1).uppercase(Locale.ROOT)
					)
					
				}
			}
		})
		
		
		
		binding.searchEt.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			
			}
			
			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			}
			
			override fun afterTextChanged(s: Editable?) {
				val keyWord = s.toString().trim { it <= ' ' }
				Log.i("test", "------------key=$keyWord")
				if (!TextUtils.isEmpty(keyWord)) {
					val searchList: ArrayList<BrandModel> = matcherSearch(keyWord, brandModelList)
					if (searchList.size > 0) {
						binding.sideBarLayout.onItemScrollUpdateSideBarText(searchList[0].brandName.substring(0, 1).uppercase(Locale.ROOT))
					}
					mAdapter.setNewInstance(searchList)
				} else {
					binding.sideBarLayout.onItemScrollUpdateSideBarText(brandModelList[0].brandName.substring(0, 1).uppercase(Locale.ROOT))
					mAdapter.setNewInstance(brandModelList)
				}
				mAdapter.notifyDataSetChanged()
			}
		})
		
		
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			val brandModel = adapter.data[position] as BrandModel
			if (brandModel.brandName.length == 1) {
				return@setOnItemClickListener
			}
			
			if (isFromFeedBack) {
				val intent = Intent()
				intent.putExtra(Constants.KEY_TV_BRAND, brandModel.brandName)
				intent.putExtra(Constants.KEY_TV_BRAND_ID, brandModel.brandId)
				setResult(RESULT_OK, intent)
				finish()
			} else {
				remoteModel.brandName = brandModel.brandName //品牌
				remoteModel.brandId = brandModel.brandId //品牌id
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, remoteModel)
				intent.setClass(this@BrandActivity, ReadyActivity::class.java)
				startActivity(intent)
			}
			
		}
	}
	
	private fun moveToPosition(position: Int) {
		if (position != -1) {
			mRecyclerView!!.scrollToPosition(position)
			val mLayoutManager = mRecyclerView!!.layoutManager as LinearLayoutManager?
			mLayoutManager!!.scrollToPositionWithOffset(position, 0)
		}
	}
	
	
	/**
	 * 匹配输入数据
	 *
	 * @param keyword
	 * @param list
	 * @return
	 */
	fun matcherSearch(keyword: String, list: List<BrandModel>): java.util.ArrayList<BrandModel> {
		val results = java.util.ArrayList<BrandModel>()
		val patten = Pattern.quote(keyword)
		val pattern = Pattern.compile(patten, Pattern.CASE_INSENSITIVE)
		for (i in list.indices) {
			//根据首字母
			val matcherWord = pattern.matcher(list[i].brandName)
			//根据拼音
//			val matcherPin = pattern.matcher(list[i].getPinyin())
//			//根据简拼
//			val matcherJianPin = pattern.matcher(list[i].getJianpin())
			//根据名字
//			val matcherName = pattern.matcher(list[i].getName())
//			if (matcherWord.find() || matcherPin.find() || matcherName.find() || matcherJianPin.find()) {
//				results.add(list[i])
//			}
			if (matcherWord.find()) {
				results.add(list[i])
			}
		}
		return results
	}
	
	
}