package com.xy.xframework.base

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xy.xframework.dialog.loading.LoadingDialogProvider
import java.lang.reflect.ParameterizedType

abstract class XBaseFragment<T : ViewDataBinding, VM : XBaseViewModel> : Fragment() {

    private var dialog: Dialog? = null


    lateinit var binding: T

    protected lateinit var viewModel: VM

    private var rootView: View? = null

    abstract fun getLayoutId(): Int

    abstract fun initVariableId(): Int

    abstract fun initView()

    open fun initArgument() {

    }

    open fun initViewObservable() {

    }

    /**
     * 处理viewModel事件
     */
    private fun registerUIChangeEventCallBack() {
        viewModel.mUiEvent.showDialogEvent.observe(this.viewLifecycleOwner, Observer {
            showLoading(it.title, it.isCancelable, it.isCancelOutside, it.onCancelListener)
        })
        viewModel.mUiEvent.dismissDialogEvent.observe(this.viewLifecycleOwner, Observer {
            dismissLoading()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (rootView != null) {
            return rootView
        }
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initArgument()
        initViewDataBinding()
        registerUIChangeEventCallBack()
        initView()
        initViewObservable()
    }


    private fun initViewModel() {
        val viewModelType = getViewModelType()
        viewModel = createViewModel(this, viewModelType as Class<ViewModel>) as VM
    }

    /**
     * 获取viewModel类型
     */
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

    /**
     * 创建ViewModel  这个方法是私有的
     */
    protected open fun <T : ViewModel> createViewModel(fragment: Fragment, cls: Class<T>): T {
        return ViewModelProvider(
            fragment,
            AndroidViewModelFactory(requireActivity().application, ::createViewModel)
        )[cls]
    }

    /**
     * 创建ViewModel  如果有viewModel的构造有多个参数则使用该方法
     */
    protected open fun createViewModel(): VM {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            .create(getViewModelType() as Class<VM>)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoading()
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
        if (dialog == null) {
            context?.let {
                dialog = LoadingDialogProvider.createLoadingDialog(it, title)
            }
        }
        dialog?.setCancelable(isCancelable)
        dialog?.setCanceledOnTouchOutside(isCancelOutside)
        dialog?.setOnCancelListener(onCancelListener)
        dialog?.show()
    }


    /**
     * binding绑定处理
     */
    @Suppress("UNCHECKED_CAST")
    private fun initViewDataBinding() {
        val viewModelId = initVariableId()
        binding.setVariable(viewModelId, viewModel)
        binding.lifecycleOwner = this
    }
}