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


        //域名 上架修改
//        const val releaseBaseUrl = "https://beta-api.qiyoutui.com"
//        const val debugBaseUrl = "https://beta-api.qiyoutui.com"
        const val releaseBaseUrl = "https://api.qiyoutui.com"
        const val debugBaseUrl = "https://api.qiyoutui.com"


        var WebFileUrl :String = "https://qiyoutui.oss-cn-shanghai.aliyuncs.com/app/debug/resource/qiyoutui.zip"

        //会员中心
        const val vipOrderUrl = "http://h5.qiyoutui.com/activity?token="



        //apk 版本  web 版本信息
        const val APK_VERSION =
            "http://sh-doukeliu-oss.oss-cn-shanghai.aliyuncs.com/doukeliu/apk/app_version.json"
        const val APK_WEB_VERSION_DEBUG =
            "http://sh-doukeliu-oss.oss-cn-shanghai.aliyuncs.com/doukeliu/app/debug/resource/dist.json"


//    上架修改
        const val APK_WEB_VERSION_RELEASE =
            "http://sh-doukeliu-oss.oss-cn-shanghai.aliyuncs.com/doukeliu/app/publish/resource/dist.json"


        //注册协议
        const val AGREEMENT_PRIVACY = "http://h5.qiyoutui.com/agreement/privacy.html"
        const val AGREEMENT_SERVICE = "http://h5.qiyoutui.com/agreement/user.html"


        //微信appid
//        const val WX_APP_ID="wxb66e1a6406d75cbc"
        const val WX_APP_ID = "wx760c6f8fb2936174"


        var FILE_IMG_VIDEO =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString()


        //切换正式 测试环境  正式  true    false debug
        const val KEY_APP_RELEASE = "key_app_release"
        const val KEY_PRIVACY = "key_privacy"
        const val KEY_USER = "key_user"

        //web版本号
        const val KEY_WEB_CODE = "key_web_code"
        const val KEY_LOCATION = "key_location"

        //抖音授权
        const val KEY_DY_TASK = "key_dy_task_id"
        const val KEY_DY_USER = "key_dy_used_id"
        const val KEY_DY_FILE = "key_dy_file_path"

        const val KEY_DY_AUTH = "key_dy_auth"
        const val KEY_DY_SHARE = "key_dy_share"


        const val KEY_SEARCH_HISTORY = "key_search_history"


        const val EVENT_TYPE = "event_type"
        const val EVENT_SEARCH_LIST = "event_search_list"
        const val EVENT_SEARCH_SPINNER = "event_search_spinner"  //4 删除全部客源

        const val EVENT_SELECT = "event_select"  //批量操作
        const val EVENT_SELECT_CUSTOM = "event_select_custom"  //批量操作
        const val EVENT_TASK_PL = "event_task_pl"  //脚本评论


        const val WEB_ZIP_NAME = "/qiyoutui.zip"

        const val FILE_CACHE_APP = "app_cache"

        //zip 包存放文件名
        const val WEB_NAME = "/qiyoutui"


        const val WEB_URL = "web_url"
        const val WEB_URL_NAME = "web_url_name"


        const val KEY_TASK_MODEL = "taskModel"

        const val KEY_TASK_VIDEO_JG = "taskVideoJG"
        const val KEY_TASK_VIDEO_JG_STR = "taskVideoJGSTR"
        const val KEY_CITY = "CityEntity"  //省市区
        const val KEY_SCREEN = "screen"  //筛选


        const val SP_POWER = "SP_POWER"  //权限
        const val SP_FOLLOW = "SP_FOLLOW"  //关注
        const val SP_ZAN = "SP_ZAN"  //
        const val SP_REMARK = "SP_REMARK"  //
        const val SP_OSS = "oss"  //阿里云


        const val EV_PHONE = "ev_phone"


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