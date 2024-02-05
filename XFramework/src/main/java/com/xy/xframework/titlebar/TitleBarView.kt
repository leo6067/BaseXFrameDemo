package com.xy.xframework.titlebar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.xy.xframework.R

/**
 * 基础状态栏
 */
class TitleBarView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
	private var ivBack: ImageView? = null
	private var ivRight: ImageView? = null
	var tvTitle: TextView? = null
	private var tvRight: TextView? = null
	
	
	init {
		LayoutInflater.from(context).inflate(R.layout.title_bar, this)
		ivBack = findViewById(R.id.ivBack)
		ivRight = findViewById(R.id.ivRight)
		tvTitle = findViewById(R.id.tvTitle)
		tvRight = findViewById(R.id.tvRight)
		setBackgroundResource(R.color.color_FFFFFF)
	}
	
	
	fun hideBackBtn() {
		ivBack?.visibility = View.GONE
	}
	
	
	fun setLeftClickListener(clickListener: OnClickListener?) {
		if (clickListener != null) {
			ivBack?.setOnClickListener(clickListener)
		}
	}
	
	fun setTitle(titleText: String) {
		tvTitle?.text = titleText
		tvTitle?.visibility = View.VISIBLE
	}
	
	fun getTitle() = tvTitle?.text.toString()
	
	fun setRightImage(drawable: Drawable?) {
		drawable?.run {
			tvRight?.visibility = View.VISIBLE
			tvRight?.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
		}
	}
	
	fun setRightText(rightText: String) {
		tvRight?.text = rightText
		tvRight?.visibility = View.VISIBLE
	}
	
	fun setRightClickListener(clickListener: OnClickListener) {
		tvRight?.setOnClickListener(clickListener)
	}
	
	/**
	 * 设置标题栏属性
	 * @param titleBarBuilder TitleBarBuilder
	 */
	fun setTitleBarBuilder(titleBarBuilder: TitleBarBuilder?) {
		titleBarBuilder ?: return
		
		tvTitle?.setTextColor(titleBarBuilder.getTitleTextColor())
		tvTitle?.paint?.isFakeBoldText = titleBarBuilder.getTitleTextBold()
		when (titleBarBuilder.getTitleGravity()) {
			TitleBarBuilder.LEFT -> {
				val lp = tvTitle?.layoutParams as LayoutParams
				lp.removeRule(CENTER_IN_PARENT)
				lp.addRule(RIGHT_OF, R.id.ivBack)
				tvTitle?.layoutParams = lp
			}
			
			TitleBarBuilder.CENTER -> {
				val lp = tvTitle?.layoutParams as LayoutParams
				lp.removeRule(RIGHT_OF)
				lp.addRule(CENTER_IN_PARENT)
				tvTitle?.layoutParams = lp
			}
		}
		setBackgroundColor(titleBarBuilder.getTitleBarBackgroundColor())
		ivBack?.setImageDrawable(titleBarBuilder.getTitleBarLeftBackIcon())
		
	}
	
	/**
	 * 获取右边textView
	 */
	fun getRightTextView(): TextView? {
		return tvRight
	}
	
 
	fun setBackImage(drawable: Drawable?) {
		drawable?.run {
			tvRight?.visibility = View.VISIBLE
			tvRight?.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
		}
	}
	
}