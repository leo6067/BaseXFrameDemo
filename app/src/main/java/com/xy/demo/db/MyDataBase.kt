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


//数据库信息   数据库版本
@Database(
	entities = [RemoteModel::class,AirModel::class],
	version = 3,
	exportSchema = false
)
abstract class MyDataBase : RoomDatabase() {
	
	abstract fun RemoteDao(): RemoteDao
	abstract fun AirDao(): AirDao
	
	
	companion object {
		
		
		/**
		 * 升级数据库
		 */
		val MIGRATION_2_3: Migration = object : Migration(2,3) {
			override fun migrate(@NonNull database: SupportSQLiteDatabase) {
//				database.execSQL("CREATE TABLE IF NOT EXISTS air(id INTEGER PRIMARY KEY NOT NULL, brandName TEXT," +
//						"brandId TEXT)")  增加表
				database.execSQL("ALTER TABLE remote ADD COLUMN tcInt INTEGER NOT NULL DEFAULT 26");
				database.execSQL("ALTER TABLE remote ADD COLUMN speedInt INTEGER NOT NULL DEFAULT 0");
				database.execSQL("ALTER TABLE remote ADD COLUMN modeInt INTEGER NOT NULL DEFAULT 0");
				database.execSQL("ALTER TABLE remote ADD COLUMN swingInt INTEGER NOT NULL DEFAULT 0");
				database.execSQL("ALTER TABLE remote ADD COLUMN isOpen INTEGER NOT NULL DEFAULT 0");
				database.execSQL("ALTER TABLE remote ADD COLUMN isNewAc INTEGER NOT NULL DEFAULT 0");
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
			.addMigrations(MIGRATION_2_3)
	        .build()
	}
}