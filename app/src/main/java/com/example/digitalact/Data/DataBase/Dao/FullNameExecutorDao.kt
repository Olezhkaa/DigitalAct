package com.example.digitalact.Data.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.digitalact.Data.DataBase.Entity.FullNameExecutorEntity

@Dao
interface FullNameExecutorDao {
    @Insert(entity = FullNameExecutorEntity::class)
    fun insertNewFullNameExecutorData(fullNameExecutor: FullNameExecutorEntity)

    @Query("SELECT * FROM fullNameExecutor")
    fun getAllFullNameExecutorData(): List<FullNameExecutorEntity>


    @Query("DELETE FROM fullNameExecutor WHERE id = :idFullNameExecutor")
    fun deleteFullNameExecutorDataById(idFullNameExecutor: Long)
}