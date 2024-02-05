package com.xy.xframework.utils

import android.app.Activity
import android.content.*
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.provider.ContactsContract.*

import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions


import org.json.JSONArray
import org.json.JSONObject


import java.util.concurrent.Executors


class AddContact {
    companion object {
        val instance: AddContact by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AddContact()
        }
    }

    private val executor by lazy { Executors.newSingleThreadExecutor() }

    private fun groupTitle(context: Context): String {
//        return if (BuildConfig.DEBUG) Utility.getAppName(context) + "1" else Utility.getAppName(context)

        return "企优推"
    }

    /**
     * 导入通讯录
     * @param progress 进度回调
     * @param listener 完成回调
     */
    fun insertContacts(
        context: Context,
        jsonArray: JSONArray?,
        progress: ((Int, Int) -> Unit)? = null,
        listener: ((Boolean, String?) -> Unit)? = null
    ) {
        val mHandler = Handler(Looper.getMainLooper())
        XXPermissions.with(context)
            .permission(Permission.READ_CONTACTS, Permission.WRITE_CONTACTS)
            .request { _, all ->
                if (all) {
                    Globals.log("xxxxxx----")
                    if (jsonArray != null) {
                        executor.submit {
//                            val groupId = jsonArray title ?: createGroup(context, groupTitle(context))
                            val groupId = findGroup(context, groupTitle(context)) ?: createGroup(
                                context,
                                groupTitle(context)
                            )
                            val exists = ArrayList<String>()
                            val length = jsonArray.length()
                            Globals.log("xxxxxx====" + jsonArray.toString())
                            (0 until length).forEach { idx ->
                                val item = jsonArray.optJSONObject(idx)
                                Globals.log("xxxxxx====item$item")
//                                val success = insert(context, item, groupId)
                                val success = insert(context, item)
                                if (!success) { // 成功添加，扣除计数
                                    exists.add(item.optString("customerName"))
                                }
                                mHandler.post {
                                    progress?.invoke(idx + 1, length)
                                }
                            }


                            val msg = when {
                                exists.size > 1 -> exists.first() + "等${exists.size}个企业的联系人电话已存在"
                                exists.isNotEmpty() -> exists.first() + "联系人电话已存在"
                                else -> null
                            }
//                            log.i(false, "[CONTACT] export contact result: $msg")
                            mHandler.post { listener?.invoke(true, msg) }

                            /*val ops = ArrayList<ContentProviderOperation>()
                            var rawContactInsertIndex = 0
                            val length = extraJson.length()
                            log.i("import contact size is : $length")
                            (0 until length).forEach { idx ->
                                rawContactInsertIndex = ops.size
                                val data = packContact(extraJson.optJSONObject(idx), groupId, rawContactInsertIndex)
                                ops.addAll(data)
                                if (idx % 50 == 0 && ops.isNotEmpty()) {
                                    log.i("import index: $idx")
                                    context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
                                    ops.clear()
                                }
                            }
                            val count = if (ops.isNotEmpty()) {
                                context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
                            } else -1
                            log.i(true, "[CONTACT] export contact result: $count")*/
                        }
                    } else {
                        listener?.invoke(false, "没有可导入的联系人！")
//                        log.w(false, "[CONTACT] No export contacts! 没有可导入的联系人！")
                    }
                } else {
                    val msg = "没有读取与写入通讯录的权限！"
//                    log.w(false, "[CONTACT] No write contacts permission! $msg！")
                    listener?.invoke(false, msg)
                }
            }
    }

    public fun insert(context: Context, contactJson: JSONObject): Boolean {
        val groupId =
            findGroup(context, groupTitle(context)) ?: createGroup(context, groupTitle(context))


        val phone = contactJson.optString("phone")
        val name = "qyt-" + contactJson.optString("name")

        Globals.log("xxxxxx", name + ":  " + phone)
        val rawId = insertContact(context, name, phone, "", null)
        if (rawId != null) {
            addInGroup(context, groupId, rawId)
            Globals.log("xxxxxx0000", name + ":  " + phone)
            return true
        }

        return false
    }


    public fun insertContact(
        context: Context,
        name: String,
        phone: String,
        company: String? = null,
        job: String? = null
    ): Long? {
//        log.w(true, "[CONTACT] insert $name, $phone, $company")

        if (!isExistNumber(context, phone)) {
            val values = ContentValues()
            val rawContactUri = context.contentResolver.insert(RawContacts.CONTENT_URI, values)
            if (rawContactUri != null) {
                val rawContactId = ContentUris.parseId(rawContactUri)
                // 表插入姓名数据
                values.clear()
                values.put(Data.RAW_CONTACT_ID, rawContactId)
                values.put(Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)// 内容类型
                values.put(CommonDataKinds.StructuredName.GIVEN_NAME, name)
                context.contentResolver.insert(Data.CONTENT_URI, values)

                // 写入公司
                if (company != null) {
                    values.clear()
                    values.put(Data.RAW_CONTACT_ID, rawContactId)
                    values.put(Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    values.put(CommonDataKinds.Organization.COMPANY, company)
                    context.contentResolver.insert(Data.CONTENT_URI, values)
                }

                // 写入工作职位
                if (job != null) {
                    values.clear()
                    values.put(Data.RAW_CONTACT_ID, rawContactId)
                    values.put(Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    values.put(CommonDataKinds.Organization.JOB_DESCRIPTION, job)
                    context.contentResolver.insert(Data.CONTENT_URI, values)
                }

                //写入电话
                values.clear()
                values.put(Data.RAW_CONTACT_ID, rawContactId)
                values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                values.put(CommonDataKinds.Phone.NUMBER, phone)
                values.put(CommonDataKinds.Phone.TYPE, phone.let {
//                    if (Utils.isPhoneNumber(it)) {
                    CommonDataKinds.Phone.TYPE_MOBILE
//                    } else {
//                        CommonDataKinds.Phone.TYPE_WORK
//                    }
                })
                context.contentResolver.insert(Data.CONTENT_URI, values)

                return rawContactId
            }
        }

        return null
    }

    private fun isExistNumber(context: Context, phone: String): Boolean {
        var find = false
        context.contentResolver.query(
            CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(PhoneLookup.DISPLAY_NAME, CommonDataKinds.Phone.NUMBER),
            CommonDataKinds.Phone.NUMBER + "=?",
            arrayOf(phone),
            null
        )?.also { find = it.count > 0 }?.apply { close() }

        return find
    }

    private fun findGroup(context: Context, name: String): Long? {
        var groupId: Long? = null

//        log.i(false, "【AddContactPlugin】读取通讯录权限permission granted: ${XXPermissions.isGranted(context, Permission.READ_CONTACTS)}")
        context.contentResolver.query(
            Groups.CONTENT_URI,
            arrayOf(Groups._ID, Groups.TITLE),
            Groups.TITLE + "=?",
            arrayOf(name),
            null
        )?.also {
            if (it.moveToFirst()) {
                val columnId = it.getColumnIndex(Groups._ID)
                groupId = it.getLong(columnId) // 组id
//                log.w("find group id is : $groupId")
            }
        }?.close()

        return groupId
    }

    /**
     * 创建联系人群组
     * @param   groupName 群组名称
     * @return  如果群组名称不为空，则返回群组id，否则返回null
     */
    private fun createGroup(context: Context, groupName: String): Long? {
        if (groupName.isNotEmpty()) {
            val values = ContentValues()
            values.put(Groups.TITLE, groupName)
            val groupUri = context.contentResolver.insert(Groups.CONTENT_URI, values)

            return groupUri?.let { ContentUris.parseId(it) }
        }

        return null
    }

    /**
     * 将联系人添加进群组
     * @param   groupId 群组id
     * @param   contactId   要添加联系人id
     */
    private fun addInGroup(context: Context, groupId: Long?, contactId: Long) {
        if (groupId != null) {
            val values = ContentValues()
            values.put(CommonDataKinds.GroupMembership.RAW_CONTACT_ID, contactId)
            values.put(CommonDataKinds.GroupMembership.GROUP_ROW_ID, groupId)
            values.put(
                CommonDataKinds.GroupMembership.MIMETYPE,
                CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
            )
            context.contentResolver.insert(Data.CONTENT_URI, values)
        }
    }


    fun removeGroupContacts(context: Context) {

        val groupId =
            findGroup(context, groupTitle(context)) ?: createGroup(context, groupTitle(context))


        getGroupContacts(context, groupId).forEach {
            // 从分组中移除
            removeFromGroup(context, it, groupId)
            // 删除Contacts表中的数据

            context.contentResolver.delete(
                RawContacts.CONTENT_URI,
                RawContacts.CONTACT_ID + "=?",
                arrayOf("$it")
            )

            context.contentResolver.delete(
                Data.CONTENT_URI,
                Data.RAW_CONTACT_ID + "=?",
                arrayOf("$it")
            )
            // 删除RawContacts表的数据
        }
    }

    fun removeImportContacts(context: Context, listener: (() -> Unit)? = null) {
        val groupId = findGroup(context, groupTitle(context))
        if (groupId != null) {
            val mHandler = Handler(Looper.getMainLooper())
            executor.submit {
                val ops = ArrayList<ContentProviderOperation>()
                getGroupContacts(context, groupId).forEachIndexed { index, it ->
                    // 从分组中移除
                    ops.add(
                        ContentProviderOperation.newDelete(Data.CONTENT_URI)
                            .withSelection(
                                CommonDataKinds.GroupMembership.RAW_CONTACT_ID + "=? and " +
                                        CommonDataKinds.GroupMembership.GROUP_ROW_ID + "=? and " +
                                        CommonDataKinds.GroupMembership.MIMETYPE + "=?",
                                arrayOf(
                                    "$it",
                                    "$groupId",
                                    CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
                                )
                            )
                            .withYieldAllowed(true).build()
                    )

                    // 删除Contacts表中的数据
                    ops.add(
                        ContentProviderOperation.newDelete(Data.CONTENT_URI)
                            .withSelection(Data.RAW_CONTACT_ID + "=?", arrayOf("$it"))
                            .withYieldAllowed(true).build()
                    )

                    // 删除RawContacts表的数据
                    ops.add(
                        ContentProviderOperation.newDelete(RawContacts.CONTENT_URI)
                            .withSelection(RawContacts.CONTACT_ID + "=?", arrayOf("$it"))
                            .withYieldAllowed(true).build()
                    )

                    if (index % 50 == 0 && ops.isNotEmpty()) {
                        context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
                        ops.clear()
                    }
                }

                // 删除群组
                val uri = ContentUris.withAppendedId(Groups.CONTENT_URI, groupId)
                    .buildUpon()
                    .appendQueryParameter(CALLER_IS_SYNCADAPTER, "true")
                    .build()
                ops.add(ContentProviderOperation.newDelete(uri).withYieldAllowed(true).build())

                if (ops.size == 50) {
                    context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
                    ops.clear()
                }
                mHandler.post { listener?.invoke() }
            }
        } else {
            listener?.invoke()
        }
    }

    fun getGroupContactsSize(context: Context): Int {
        val groupId = findGroup(context, groupTitle(context))
        return if (groupId != null) {
            getGroupContacts(context, groupId).size
        } else {
            0
        }
    }

    fun getGroupContacts(context: Context): ArrayList<Long> =
        getGroupContacts(context, findGroup(context, groupTitle(context)))

    /**
     * 获取群组联系人
     * @param   groupId 群组ID
     * @return  联系人ID列表
     */
    private fun getGroupContacts(context: Context, groupId: Long?): ArrayList<Long> {
        val contactList = ArrayList<Long>()
        if (groupId != null) {
            // 通过分组的id 查询得到RAW_CONTACT_ID
            context.contentResolver.query(
                Data.CONTENT_URI,
                arrayOf(Data.RAW_CONTACT_ID),
                CommonDataKinds.GroupMembership.GROUP_ROW_ID + "=? and " + Data.MIMETYPE + "=?",
                arrayOf("$groupId", CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE),
                CommonDataKinds.GroupMembership.GROUP_ROW_ID + " asc"
            )?.also {
                while (it.moveToNext()) {
                    val columId = it.getColumnIndex(Data.RAW_CONTACT_ID)
                    val rawContactId = it.getLong(columId)
                    contactList.add(rawContactId)
//                    log.i("raw_contact_id: $rawContactId")
                }
            }?.close()
        }

        return contactList
    }

    /**
     * 将联系人从群组中移除
     * @param   contactId   联系人Id
     * @param   groupId     群组ID
     */
    private fun removeFromGroup(context: Context, contactId: Long, groupId: Long?) {
        if (groupId != null) {
            context.contentResolver.delete(
                Data.CONTENT_URI,
                CommonDataKinds.GroupMembership.RAW_CONTACT_ID + "=? and " +
                        CommonDataKinds.GroupMembership.GROUP_ROW_ID + "=? and " +
                        CommonDataKinds.GroupMembership.MIMETYPE + "=?",
                arrayOf("$contactId", "$groupId", CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)
            )
        }
    }

    private fun deleteGroup(context: Context, groupId: Long?) {
        if (groupId != null) {
            // 做删除标志，但未真正删除。真正的删除会在同步时自动实现
//            context.contentResolver.delete(Data.CONTENT_URI, Groups._ID + "=?", arrayOf("$groupId"))
            val ub = ContentUris.withAppendedId(Groups.CONTENT_URI, groupId).buildUpon()
            ub.appendQueryParameter(CALLER_IS_SYNCADAPTER, "true")
            val uri = ub.build()
            context.contentResolver.delete(uri, null, null)
        }
    }

    fun isLocalGroup(context: Context, gid: Long): Boolean =
        findGroup(context, groupTitle(context)) == gid


}