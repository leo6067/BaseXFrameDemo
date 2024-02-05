package com.xy.demo.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RemoteDao : BaseDao<RemoteModel> {
	
	
 
	
	// 搜索 remote 表中 符合的参数 对象
	@Query("select * from remote where name = :name")
	fun getByName(name: String): RemoteModel?
	
	/**
	 * 查询表里所有数据
	 */
	@Query("select * from remote")
	fun getAllRemote(): List<RemoteModel>
	
	/**
	 * 根据字段删除记录
	 */
	@Query("delete from remote where name = :name")
	fun deleteByName(name: String)
	
	
	/**
	 * 修改指定设备名 的 指令
	 */
	@Query("update remote set orderStr =:orderStr where name =:name")
	fun updateJson(name: String, orderStr: String)
	
}
