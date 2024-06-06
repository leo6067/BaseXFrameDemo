package com.xy.demo.db

import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.xy.demo.base.Constants
import com.xy.demo.base.MyApplication
import com.xy.demo.network.Globals


//数据库信息   数据库版本   多表
@Database(
	entities = [PdfFileModel::class,SDPdfFileModel::class],
	version = 2,
	exportSchema = false
)
abstract class MyDataBase : RoomDatabase() {
	
	abstract fun PdfFileDao(): PdfFileDao
	
	//SDPdfFileModel
//	abstract fun PdfFileDao(): PdfFileDao
	

	
	
	companion object {
		
		
		/**
		 * 升级数据库
		 */
		val MIGRATION_1_2: Migration = object : Migration(1, 2) {
			override fun migrate(@NonNull database: SupportSQLiteDatabase) {
				// database.execSQL("ALTER TABLE user ADD COLUMN subject INTEGER NOT NULL DEFAULT 1");
				database.execSQL("CREATE TABLE animal(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT)")
			}
		}
		
		
		val instance = Room.databaseBuilder(MyApplication.instance, MyDataBase::class.java, Constants.DB_NAME)
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
			.addMigrations(MIGRATION_1_2)
	        .build()
	}
}