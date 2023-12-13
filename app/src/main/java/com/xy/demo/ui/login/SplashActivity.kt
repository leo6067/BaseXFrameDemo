package com.xy.demo.ui.login

import android.content.Intent
import com.jeremyliao.liveeventbus.LiveEventBus
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivitySplashBinding
import com.xy.demo.event.DialogEvent
import com.xy.demo.network.NetLaunchManager
import com.xy.demo.network.NetManager
import com.xy.demo.network.params.ReaderParams
import com.xy.demo.ui.dialog.AgreementDialogFragment
import com.xy.demo.ui.main.MainActivity
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.utils.ToastUtils

class SplashActivity : MBBaseActivity<ActivitySplashBinding, MBBaseViewModel>() {


    // 弹窗
    // 是否首次打开APP
    private val isShowPrivacyDialog = false


    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun fitsSystemWindows(): Boolean {
        return false
    }

    override fun showTitleBar(): Boolean {
        return false
    }

    override fun initParams() {
        super.initParams()

        getCheckSetting()
        initAgreement()


    }


    fun getCheckSetting() {
        val readerParams = ReaderParams(this)
        NetLaunchManager.launchRequest({
            NetManager.getCheckSetting(readerParams.generateParamsMap())
        }, {
            if (it != null) {

            }

        }) {
            ToastUtils.showShort(it.message)
        }
    }


    fun initAgreement() {
        val firstOpen = BaseSharePreference.spObject.getBoolean(Constants.FIRST_OPEN, true)
        if (firstOpen) {
            var agreementDialogFragment = AgreementDialogFragment()
            agreementDialogFragment.show(supportFragmentManager, "agreementDialogFragment")
            //同意
        } else {
            enterApp()
        }


        LiveEventBus.get<DialogEvent>(Constants.dialog_back).observe(this) {
            if (it.isPressStatus) {
                enterApp()
            }else{
                System.exit(0)
            }
        }
    }


    fun enterApp() {
        val userString = BaseSharePreference.spObject.getString(Constants.USER_INFO, "")
        if (userString!!.length < 20) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}