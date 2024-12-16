package com.example.digitalact.Data.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.digitalact.Data.DataBase.Entity.TelephoneEntity

@Dao
interface TelephoneDao {

    @Insert(entity = TelephoneEntity::class)
    fun insertTelephoneData(telephone: TelephoneEntity)

    @Query("SELECT * FROM telephone")
    fun getAllTelephoneData() : List<TelephoneEntity>

    @Query("SELECT numberTelephone FROM telephone WHERE iccID = :iccID")
    fun getNumberTelephoneByICC(iccID: String) : String

    @Query("DELETE FROM telephone")
    fun deleteAllTelephoneData()
}