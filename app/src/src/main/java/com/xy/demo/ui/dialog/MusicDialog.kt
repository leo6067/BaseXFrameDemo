package com.xy.demo.ui.dialog

import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xy.demo.R
import com.xy.demo.base.MBBaseDialogFragment
import com.xy.demo.base.MyApplication
import com.xy.demo.databinding.DialogMusicBinding
import com.xy.demo.logic.file.FileManager
import com.xy.demo.model.MusicModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.adapter.MusicAdapter


class MusicDialog : MBBaseDialogFragment<DialogMusicBinding>() {
	
	
	val musicAdapter = MusicAdapter()
	
	
	override fun getLayoutId(): Int {
		return R.layout.dialog_music
	}
	
	override fun getGravity(): Int {
		return Gravity.BOTTOM
	}
	
	override fun initView() {
		
		binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
		binding.recyclerView.adapter = musicAdapter
		
		musicAdapter.setNewInstance(FileManager.getInstance(requireActivity()).musics)
	}
	
	override fun initListener() {
//	     musicAdapter.setOnItemClickListener { adapter, view, position ->
//			val musicModel = adapter.data[position] as MusicModel
//			Globals.log("xxxxxxxmusicAdapterxxxxx"+musicModel.toString())
//		}
	}
	
	
}