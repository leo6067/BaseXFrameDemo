package com.xy.xframework.utils

import java.util.*

/**
 * author: Leo
 * createDate: 2022/11/23 10:39
 */
object NumberUtil {

    /**
     * 是否为手机号码（正则表达式）
     *
     * @param mobiles
     * @return
     */
    @JvmStatic
    fun isPhoneNumber(mobiles: String?): Boolean {
        /*
        截止2017年10月各大运营商码号资源分配情况如下
        移动：134 135 136 137 138 139 144 147 148 150 151 152 157(TD) 158 159 172 178 182 183 184 187 188 197 198 （其中，不含1349和1578）
		联通：186 185 176 175 166 156 155 146 145 140 132 131 130 196
		电信：133 141 149 153 173 177 180 181 189 199（1349卫通） 190
		广电：192
		总结起来就是第一位必定为1，第二位必定为3、4、5、7、8、9，其他位置的可以为0-9
		*/
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        // ^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$
        val telRegex = "[1][3456789]\\d{9}"
//        val telRegex = "^((13[0-9])|(14[014-9])|(15[0-3,5-9])|(17[0235-8])|(18[0-9])|166|198|199)\\d{8}\$"
//        val telRegex = "^((13[0-9])|(14[014-9])|(15[0-3,5-9])|(17[0235-8])|(18[0-9])|166|(19[0-26-9]))\\d{8}\$"
//        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex.toRegex())
        return mobiles?.matches(telRegex.toRegex()) ?: false
    }

    @JvmStatic
    fun isUrl(text: CharSequence?): Boolean =
        text != null && (text.startsWith("http://") || text.startsWith("https://"))



    //final int random = new Random().nextInt(61) + 20; // [0, 60] + 20 => [20, 80]
    fun randomNum(high:Int,low:Int):Int{
        return Random().nextInt(high-low+1)+low
    }

}