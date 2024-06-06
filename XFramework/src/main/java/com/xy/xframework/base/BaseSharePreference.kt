

package com.xy.xframework.base

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Base64
import com.google.gson.Gson
import java.io.*
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*

class BaseSharePreference private constructor(mContext: Context) {
    
    private var mSharedPreferences: SharedPreferences = mContext.getSharedPreferences(BaseConstants.baseShareName, Context.MODE_PRIVATE)
    
    companion object {
        //内部使用
        private lateinit var mInstance: BaseSharePreference
        private val mSyncLock = Any()
        
        val instance: BaseSharePreference
            get() {
                synchronized(mSyncLock) {
                    mInstance = BaseSharePreference(XBaseApplication.application)
                }
                return mInstance
            }
    }
    
    
    /**
     * 保存版本升级
     */
    //    public void setVersion(String LoginInfo) {
    //        mSharedPreferences.edit().putString("setVersion", LoginInfo).commit();
    //    }
    //
    //    public VersionJson.DataBean getVersion() {
    //        VersionJson.DataBean version = null;
    //        String infoStr = mSharedPreferences.getString("setVersion", "");
    //        if (infoStr.length() > 2) {
    //            VersionJson userInfoBean = JSONObject.parseObject(infoStr, VersionJson.class);
    //            version = userInfoBean.getData();
    //        }
    //        return version;
    //    }
    /**
     * @param
     * @param key 要取的数据的键
     * @param defValue 缺省值
     * @return
     */
    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(key, defValue)
    }
    
    /**
     * 存储一个boolean类型数据
     * @param
     * @param key
     * @param value
     */
    fun putBoolean(key: String?, value: Boolean) {
        mSharedPreferences.edit().putBoolean(key, value).commit()
    }
    
    /**
     * 存储一个String类型的数据
     * @param
     * @param key
     * @param value
     */
    fun putString(key: String?, value: String?) {
        mSharedPreferences.edit().putString(key, value).commit()
    }
    
    /**
     * 获取一个String类型的数据
     * @param
     * @param key
     * @param defValue
     * @return
     */
    fun getString(key: String, defValue: String): String? {
        return mSharedPreferences.getString(key, defValue)
    }
    
    /**
     * 存储一个long类型的数据
     * @param
     * @param key
     * @param value
     */
    fun putLong(key: String?, value: Long) {
        mSharedPreferences.edit().putLong(key, value).commit()
    }
    
    /**
     * 获取一个long类型的数据
     * @param
     * @param key
     * @param defValue
     * @return
     */
    fun getLong(key: String?, defValue: Long): Long {
        return mSharedPreferences.getLong(key, defValue)
    }
    
    /**
     * 存储一个int类型的数据
     * @param
     * @param key
     * @param value
     */
    fun putInt(key: String?, value: Int) {
        mSharedPreferences.edit().putInt(key, value).commit()
    }
    
    /**
     * 获取一个int类型的数据
     * @param
     * @param key
     * @param defValue
     * @return
     */
    fun getInt(key: String?, defValue: Int): Int {
        return mSharedPreferences.getInt(key, defValue)
    }
    
    /**
     * 存放实体类以及任意类型
     * @param key
     * @param obj
     */
    fun putBean(key: String?, obj: Any?) {
        if (obj is Serializable) { // obj必须实现Serializable接口，否则会出问题
            try {
                val baos = ByteArrayOutputStream()
                val oos = ObjectOutputStream(baos)
                oos.writeObject(obj)
                val string64 = String(
                    Base64.encode(
                        baos.toByteArray(),
                        0
                    )
                )
                mSharedPreferences.edit().putString(key, string64).commit()
            } catch (e: IOException) {
            }
        } else {
            throw IllegalArgumentException(
                "the obj must implement Serializble"
            )
        }
    }
    
    fun getBean(key: String?): Any? {
        var obj: Any?
        try {
            val base64 = mSharedPreferences.getString(key, "")
            if (base64 == "") {
                return ""
            }
            val base64Bytes = Base64.decode(base64!!.toByteArray(), 1)
            val bais = ByteArrayInputStream(base64Bytes)
            val ois = ObjectInputStream(bais)
            obj = ois.readObject()
        } catch (e: Exception) {
            return ""
        }
        return obj
    }
    
    
    fun putObject(key: String?, `object`: Any?) {
        if (`object` == null) {
            mSharedPreferences.edit().putString(key, null)
        }
        mSharedPreferences.edit().putString(key, Gson().toJson(`object`))
        mSharedPreferences.edit().apply()
    }
    
    fun <T> getObject(key: String, a: Class<T>?): T? {
        val json = mSharedPreferences.getString(key, null)
        if (TextUtils.isEmpty(json)) {
            return null
        } else {
            try {
                return Gson().fromJson(json, a)
            } catch (e: Exception) {
            
            }
        }
        return null
    }
    
    /**
     * Get parsed ArrayList of String from SharedPreferences at 'key'
     *
     * @param key SharedPreferences key
     * @return ArrayList of String
     */
    fun getListString(key: String?): ArrayList<String>? {
        return ArrayList(
            Arrays.asList(
                *TextUtils.split(
                    mSharedPreferences.getString(key, ""), "‚‗‚"
                )
            )
        )
    }
    
    /**
     * Put ArrayList of String into SharedPreferences with 'key' and save
     *
     * @param key        SharedPreferences key
     * @param stringList ArrayList of String to be added
     */
    fun putListString(key: String?, stringList: ArrayList<String>) {
        checkForNullKey(key)
        val myStringList = stringList.toTypedArray()
        mSharedPreferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList))?.apply()
    }
    
    /**
     * null keys would corrupt the shared pref file and make them unreadable this is a preventive measure
     *
     * @param key the pref key
     */
    fun checkForNullKey(key: String?) {
        if (key == null) {
        
        }
    }
    
    /**
     * 存储Map集合
     *
     * @param key 键
     * @param map 存储的集合
     * @param <K> 指定Map的键
     * @param <V> 指定Map的值
    </V></K> */
    fun <K : Serializable?, V : Serializable?> putMap(key: String, map: Map<K, V>?): BaseSharePreference{
        try {
            put(key, map)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }
    
    fun <K : Serializable?, V : Serializable?> getMap(key: String): MutableMap<K, V>? {
        try {
            return get(key) as MutableMap<K, V>?
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    
    
    /**
     * 存储对象
     */
    @Throws(IOException::class)
    private fun put(key: String, obj: Any?) {
        if (obj == null) { //判断对象是否为空
            return
        }
        val baos = ByteArrayOutputStream()
        var oos: ObjectOutputStream? = null
        oos = ObjectOutputStream(baos)
        oos.writeObject(obj)
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        val objectStr = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
        baos.close()
        oos.close()
        putString(key, objectStr)
    }
    
    /**
     * 获取对象
     */
    @Throws(IOException::class, ClassNotFoundException::class)
    private operator fun get(key: String): Any? {
        val wordBase64 = getString(key, "")
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null
        }
        val objBytes = Base64.decode(wordBase64?.toByteArray(), Base64.DEFAULT)
        val bais = ByteArrayInputStream(objBytes)
        val ois = ObjectInputStream(bais)
        // 将byte数组转换成product对象
        val obj = ois.readObject()
        bais.close()
        ois.close()
        return obj
    }
}
