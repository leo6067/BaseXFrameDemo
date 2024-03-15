package com.xy.demo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface JunkDao : BaseDao<JunkModel> {
	
	
	@Insert(
		entity = JunkModel::class, // 目标实体，即插入的表
		onConflict = OnConflictStrategy.REPLACE) // 有旧数据存在，则替换掉旧数据
	fun insertA(movies: JunkModel)
	
	
	//清空数据库
	@Query("DELETE FROM junk")
	fun deleteAll()
	
	
	
	@Update(entity = JunkModel::class, onConflict = OnConflictStrategy.REPLACE)
	fun updateA(  movies: JunkModel)
 
	
	// 搜索 remote 表中 符合的参数 对象
	@Query("select * from junk where junkName = :name")
	fun getByName(name: String): JunkModel?
	
	/**
	 * 查询表里所有数据
	 */
	@Query("select * from junk")
	fun getAllJunk(): MutableList<JunkModel>
	
	/**
	 * 根据字段删除记录
	 */
	@Query("delete from junk where junkName = :name")
	fun deleteByName(name: String)
	
	
	/**
	 * 修改指定 字段   修改某垃圾的 体积大小
	 */
	@Query("update junk set junkSize =:junkSize where junkName =:name")
	fun upJunkSize(name: String, junkSize: Long)
	
	
	
	/**
	 * 修改指定 字段   修改某垃圾的 时间
	 */
	@Query("update junk set recycleTime =:recycleTime where junkName =:name")
	fun upJunkTime(name: String, recycleTime: Long)
	
}
