package com.xy.demo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

//@Dao， 使用@Dao注解定义为一个BaseDao接口，增加基本的插入、删除、修改方法，再定义一个UserDao去扩展BaseDao接口，增加需要的方法。
//
//@Insert，插入，可以定义将其参数插入数据库中的相应表的方法
//@Delete，删除，可以定义用于从数据库表中删除特定行的方法
//@Update，修改，可以定义用于更新数据库表中特定行的方法
//@Query， 查询，可以从应用的数据库查询指定数据，用于更加复杂的插入、删除、更新操作

// @Insert(
//        entity = Movie::class, // 目标实体，即插入的表
//        onConflict = OnConflictStrategy.REPLACE // 有旧数据存在，则替换掉旧数据
//    )
//    suspend fun insert(vararg movies: Movie)
//
//————————————————
//版权声明：本文为CSDN博主「一场雪ycx」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/yang553566463/article/details/125008322
@Dao
interface BaseDao<T> {
	
	//覆盖插入  有就覆盖  只能针对主键
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertOrReplace(bean: T)
	@Insert
	fun insert(bean: T)
	
	@Insert
	fun insertAll(bean: T)
	
	@Delete
	fun delete(bean:T)
	
	@Update
	fun update(bean: T)
	
}