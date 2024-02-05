package com.xy.demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xy.demo.R
import com.xy.demo.model.OrderListModel

class RemoteMoreAdapter :BaseQuickAdapter<OrderListModel.OrderModel,BaseViewHolder>(R.layout.item_remote_more) {
	override fun convert(holder: BaseViewHolder, item: OrderListModel.OrderModel) {
		holder.setText(R.id.zeroTV,item.remoteKey)
	}
}