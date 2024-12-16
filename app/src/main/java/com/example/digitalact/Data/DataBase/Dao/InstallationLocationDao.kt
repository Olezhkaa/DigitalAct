package com.example.digitalact.Data.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.digitalact.Data.DataBase.Entity.InstallationLocationEntity

@Dao
interface InstallationLocationDao {
    @Insert(entity = InstallationLocationEntity::class)
    fun insertNewInstallationLocationData(installationLocation: InstallationLocationEntity)

    @Query("SELECT * FROM installationLocation")
    fun getAllInstallationLocationData(): List<InstallationLocationEntity>


    @Query("DELETE FROM installationLocation WHERE id = :idInstallationLocation")
    fun deleteInstallationLocationDataById(idInstallationLocation: Long)
}