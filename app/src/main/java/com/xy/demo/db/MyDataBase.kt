package com.xy.demo.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.xy.demo.base.MyApplication
import com.xy.demo.network.Globals


//数据库信息   数据库版本
@Database(
	entities = [RemoteModel::class],
	version = 2,
	exportSchema = false
)
abstract class MyDataBase : RoomDatabase() {
	
	abstract fun RemoteDao(): RemoteDao
	
	
	companion object {
		val instance = Room.databaseBuilder(MyApplication.instance, MyDataBase::class.java, "remote_db")
			.addCallback(object :RoomDatabase.Callback(){
				//第一次创建数据库时调用
				override fun onCreate(db: SupportSQLiteDatabase) {
					super.onCreate(db)
					Globals.log("first onCreate db version: " + db.version)
				}
				
				override fun onOpen(db: SupportSQLiteDatabase) {
					Globals.log("first onCreate db version: " + db.path)
					
				}
				
				/**
				 * Called after the database was destructively migrated
				 *
				 * @param db The database.
				 */
				override fun onDestructiveMigration(db: SupportSQLiteDatabase) {}
			 
				
				
			})//数据库创建回调调用
	        .build()
	}
}