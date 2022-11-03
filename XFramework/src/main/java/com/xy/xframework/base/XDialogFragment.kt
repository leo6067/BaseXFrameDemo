package com.xy.xframework.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.xy.xframework.R
import com.xy.xframework.utils.ScreenUtil

abstract class XDialogFragment<VB : ViewDataBinding> : DialogFragment() {

    lateinit var binding: VB

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCancelable = cancelable()
        if (isCancelable) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable())
        }
        if (getAnimation() != -1) {
            dialog?.window?.setWindowAnimations(getAnimation())
        }
        dialog?.window?.attributes?.apply {
            gravity = getGravity()
            width = getWidth() - getMargin()
            height = getHeight()
            if (hideBackgroundShadow()) {
                dimAmount = 0f
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false)


        initView()
        initListener()
        initViewObservable()


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseCustomDialog)
    }

    open fun getAnimation(): Int {
        return -1
    }

    abstract fun initView()

    abstract fun initListener()

    abstract fun getLayoutId(): Int

    open fun initViewObservable() {

    }

    open fun getGravity(): Int = Gravity.CENTER

    open fun getWidth(): Int = ScreenUtil.getScreenWidth(requireContext())

    open fun getHeight(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    open fun cancelable(): Boolean = false

    open fun hideBackgroundShadow(): Boolean = false

    open fun getMargin(): Int = 0

    override fun show(manager: FragmentManager, tag: String?) {
        showAllowingStateLoss(manager, tag)
    }

    private fun showAllowingStateLoss(manager: FragmentManager, tag: String?) {
        try {
            val dismissed = DialogFragment::class.java.getDeclaredField("mDismissed")
            dismissed.isAccessible = true
            dismissed.set(this, false)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        try {
            val shown = DialogFragment::class.java.getDeclaredField("mShownByMe")
            shown.isAccessible = true
            shown.set(this, true)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }
}