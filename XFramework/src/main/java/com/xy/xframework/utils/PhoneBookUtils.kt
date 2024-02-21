package com.xy.xframework.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import com.alibaba.fastjson.JSON
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * author: Leo
 * createDate: 2023/5/25 13:49
 *
 * 电话簿  群发短信
 */
class PhoneBookUtils {


    companion object {


        // 可批量 添加 到通讯录
//        fun AddPhoneBook(context: Context, jsonString: String) {
//            XXPermissions.with(context)
//                .permission(Permission.WRITE_CONTACTS, Permission.READ_CONTACTS)
//                .request(OnPermissionCallback { permissions, all ->
//                    if (all) {
////                        val jsonList = com.alibaba.fastjson.JSONObject.parseArray(jsonString)
////                        for (position in jsonList.indices) {
////                            val phoneModel =
////                                JSON.parseObject(
////                                    jsonList[position].toString(),
////                                    PhoneModel::class.java
////                                )
////                            val jsonObject = JSONObject() //创建一个JSONObject存储数据
////                            jsonObject.put("phone", phoneModel.phone)
////                            jsonObject.put("name", phoneModel.name)
////                            Globals.log("xxxxxxjsonObject2222", jsonObject.toString())
////                            CoroutineScope(Dispatchers.IO).launch {
////                                Globals.log("xxxxxxjsonObject", jsonObject.toString())
////                                AddContact.instance.insert(
////                                    AppActivityManager.getCurrentActivity(),
////                                    jsonObject
////                                )
////                            }
////                        }
//
//                    } else {
//                        ToastUtils.showShort("请先授予读写通讯录权限")
//                    }
//                })
//        }


        //模糊搜索 模糊删除通讯录
//        fun deleteFuzzy(context: Context, keyword: String) {
//            XXPermissions.with(context)
//                .permission(Permission.WRITE_CONTACTS, Permission.READ_CONTACTS)
//                .request(OnPermissionCallback { permissions, all ->
//                    if (all) {
//                        searchCons(context, keyword)
//                    } else {
//                        ToastUtils.showShort("请先授予读写通讯录权限")
//                    }
//                })
//        }


        //模糊搜索  并 删除 企优推
        fun searchCons(context: Context, keyword: String) {
            val cr = context.contentResolver
            val contactList: MutableList<Map<String, String>> = ArrayList()
            val cursorName = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.PhoneLookup.DISPLAY_NAME + " like " + "'%" + keyword + "%'",
                null,
                null
            )
            while (cursorName!!.moveToNext()) {
                val columnIndex = cursorName.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                if (columnIndex > 0) {
                    val name = cursorName.getString(columnIndex)
                    val contactIndex = cursorName.getColumnIndex(ContactsContract.Contacts._ID)
                    if (contactIndex > 0) {
                        val contactId = cursorName.getString(contactIndex)
                        val phone = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                            null,
                            null
                        )
                        while (phone!!.moveToNext()) {
                            val map: MutableMap<String, String> = HashMap()
                            val NUMBERIndex =
                                phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            val phoneNumber =
                                phone.getString(NUMBERIndex)
//                            val photo =
//                                phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI))
                            map["name"] = name
                            map["phoneNum"] = phoneNumber
//                            map["photo"] = photo
                            Log.e("xxxxxxmap", map.toString())
//                            contactList.add(map)
                            var deteleName = arrayOf(name)
                            var deteleId = arrayOf(contactId)
                            CoroutineScope(Dispatchers.IO).launch {
                                cr.delete(
                                    ContactsContract.Data.CONTENT_URI,
                                    ContactsContract.Contacts.DISPLAY_NAME + "=?",
                                    deteleName
                                );

                                cr.delete(
                                    ContactsContract.Data.CONTENT_URI,
                                    ContactsContract.Data.RAW_CONTACT_ID + "=?",
                                    deteleId
                                );
                            }
                        }
                    }
                }
            }
            cursorName.close()
        }
    }


    //群发短信
    fun sendSms(context: Context, phoneListStr: String) {

        XXPermissions.with(context)
            .permission(Permission.SEND_SMS)
            .request(OnPermissionCallback { permissions, all ->
                if (all) {
                    val jsonObject = JSONObject(phoneListStr)
                    var phoneList = jsonObject.getString("phoneList")
                    var content = jsonObject.getString("content")


                    var phoneStr = ""
                    if (phoneList.contains(",")) {
                        var phoneArray = phoneList.split(",") as ArrayList
                        for (phone in phoneArray) {
                            phoneStr = "$phone;$phoneStr"
                        }
                    } else {
                        phoneStr = phoneList
                    }

                    val sendIntent =
                        Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneStr))
                    sendIntent.putExtra("sms_body", content)
                    context.startActivity(sendIntent)
                } else {
                    ToastUtils.showShort("请先授予发短信权限")
                }
            })

    }


}