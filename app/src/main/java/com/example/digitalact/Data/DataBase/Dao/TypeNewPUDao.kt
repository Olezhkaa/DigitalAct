package com.example.digitalact.Data.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.digitalact.Data.DataBase.Entity.TypeNewPUEntity

@Dao
interface TypeNewPUDao {
    @Insert(entity = TypeNewPUEntity::class)
    fun insertNewTypeNewPUData(typeNewPU: TypeNewPUEntity)

    @Query("SELECT * FROM typeNewPU")
    fun getAllTypeNewPUData(): List<TypeNewPUEntity>


    @Query("DELETE FROM typeNewPU WHERE id = :idTypeNewPU")
    fun deleteTypeNewPUDataById(idTypeNewPU: Long)
}