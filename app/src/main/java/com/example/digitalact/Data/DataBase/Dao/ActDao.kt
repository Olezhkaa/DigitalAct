package com.example.digitalact.Data.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.digitalact.Data.DataBase.Entity.ActEntity
import com.example.digitalact.Data.DataBase.Entity.TaskEntity

@Dao
interface ActDao {
    @Insert(entity = ActEntity::class)
    fun insertNewActData(act: ActEntity)

    @Query("SELECT * FROM acts")
    fun getAllActData(): List<ActEntity>

    @Query("SELECT * FROM acts WHERE id = :idAct")
    fun getActDataByID(idAct: Long): ActEntity

    @Query("DELETE FROM acts WHERE id = :idAct")
    fun deleteActDataById(idAct: Long)

    @Query("DELETE FROM acts")
    fun deleteAllActData()

    @Update(entity = ActEntity::class)
    fun updateActData(act: ActEntity)

}