package com.xy.demo.base

import android.content.Context
import android.os.Environment



/**
 * author: Leo
 * createDate: 2022/11/22 19:17
 */
class Constants {
//    fAAzohwDUKpfqG0hRqyLHLdlTk+PsJqoiouI01Cmq1lYr5UU6mwSOCcF1wcy75TcI0MDrU0b81tLhyRsN2rjqoLRdrFHoKAf2rbvggUyTEQsDGSW/ePo0Ko8RDf0dzZOQx0TsImMDBHjPqtRLqpp5Xu8EQupEctnVDWJzspirR+Oz+vjzj3PIqscp2NgI+TZSvCR3BNtQRDeZrtBhXNGq6GCGK3EucaGj5gWlioqO1Rf1PUXueOiHVuR7ehB/GqAdD/YuPhEqx6Bu832En/Ye7oIfbikFbIK0pgWAFnd+x5v3ifq3lIBQA==

    companion object {

        var FILE_IMG_VIDEO =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString()



        // event key

        val dialog_back :String = "dialog_back"



        //sp constants

        val FIRST_OPEN :String = "first_open"
        val USER_INFO :String = "user_info"




//        fun assembleAgreement(context: Context, agreementCheckbox: CheckBox) {
//            val span = SpannableStringBuilder("我已阅读并同意")
//            val agreement = SpannableString("《用户协议》")
//            agreement.setSpan(object : ClickableSpan() {
//                override fun onClick(v: View) {
//                    AgreementActivity.startServer(context)
////                val intent = Intent(this@LoginActivity, AuthWebVeiwActivity::class.java)
////                intent.putExtra("url", Constant.AGREEMENT_SERVICE)
////                intent.putExtra("name", "服务协议")
////                startActivity(intent)
//                }
//
//                override fun updateDrawState(ds: TextPaint) {
//                    super.updateDrawState(ds)
//                    //设置字体颜色
//                    ds.color = ContextCompat.getColor(context, R.color.colorPrimary)
//                    ds.isUnderlineText = false //去掉下划线
//                    ds.bgColor = ContextCompat.getColor(context, R.color.background_color)
//                }
//            }, 0, agreement.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//            span.append(agreement)
//
//            val privacy = SpannableString("《隐私协议》")
//            privacy.setSpan(object : ClickableSpan() {
//                override fun onClick(p0: View) {
//                    AgreementActivity.startPrivacy(context)
////                val intent = Intent(this@LoginActivity, AuthWebVeiwActivity::class.java)
////                intent.putExtra("url", Constant.AGREEMENT_PRIVACY)
////                intent.putExtra("name", "隐私政策")
////                startActivity(intent)
//                }
//
//                override fun updateDrawState(ds: TextPaint) {
//                    super.updateDrawState(ds)
//                    //设置字体颜色
//                    ds.color = ContextCompat.getColor(context, R.color.colorPrimary)
//                    ds.isUnderlineText = false //去掉下划线
//                    ds.bgColor = ContextCompat.getColor(context, R.color.background_color)
//                }
//            }, 0, privacy.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//            span.append(privacy)
//
//            agreementCheckbox.text = span
//            agreementCheckbox.movementMethod = LinkMovementMethod.getInstance()
//        }





    }
}