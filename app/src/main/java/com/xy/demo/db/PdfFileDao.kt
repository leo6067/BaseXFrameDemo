package com.xy.demo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PdfFileDao : BaseDao<PdfFileModel> {
	
	
	// 搜索 remote 表中 符合的参数 对象
	@Query("select * from pdfFile where file_name = :name")
	fun getByName(name: String): PdfFileModel?
	
	/**
	 * 查询表里所有数据
	 */
	@Query("select * from pdfFile")
	fun getAllPdfList(): MutableList<PdfFileModel>
	
	/**
	 * 根据字段删除记录
	 */
	@Query("delete from pdfFile where file_name = :name")
	fun deleteByName(name: String)
	
 
	
	/**
	 * 修改指定 参数
	 */
	@Query("update pdfFile set last_time =:time where file_name =:name")
	fun updateJson(name: String, time: String)
	
	
	/**
	 * 修改 文件名 同时更新文件路径
	 */
	@Query("update pdfFile set file_name =:newName,file_path = :filePath where file_name =:oldName")
	fun updateFile(newName: String, oldName: String, filePath: String)
	
	
	
	
	@Query("select uid from pdfFile WHERE file_path = :pathValue")
	fun getIdByPath(pathValue: String): Long?
	
	
	@Query("DELETE FROM pdfFile WHERE file_path = :pathValue")
	fun deleteByPath(pathValue: String): Int
	
 
	
	// 你可以封装上述三个操作为一个方法来实现先检查再删除再插入的逻辑
	suspend fun insertOrReplaceByPath(entity: PdfFileModel) {
		val id = getIdByPath(entity.path)
		if (id != null) {
			deleteByPath(entity.path)
		}
		insert(entity)
	}
 
	
	
}
