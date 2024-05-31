package com.xy.demo.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface AirDao : BaseDao<AirModel> {
	
	
	@Insert
	fun insert(air: AirModel?)
	
	
}
