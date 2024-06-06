package com.xy.demo.ui.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xy.demo.R
import com.xy.demo.model.ImgModel

class ImageAdapter : BaseQuickAdapter<ImgModel, BaseViewHolder>(R.layout.item_image) {
	override fun convert(viewHolder: BaseViewHolder, bean: ImgModel) {
		val imageView = viewHolder.getView<ImageView>(R.id.imageView)
		
		// 设置边角半径为10dp
		val cornerRadius = 10 // 可以根据需要调整
		val options = RequestOptions().transform(RoundedCorners(cornerRadius))

        // 使用Glide加载图片并应用边角转换
		Glide.with(imageView.context)
			.asBitmap()
			.load(bean.imgPath)
			.apply(options)
			.into(object : CustomTarget<Bitmap>() {
				override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap?>?) {
					bean.setBitmapImg(resource)
					imageView.setImageBitmap(resource)
				}
				
				override fun onLoadCleared(placeholder: Drawable?) {
				
				}
				
			})
	}
}