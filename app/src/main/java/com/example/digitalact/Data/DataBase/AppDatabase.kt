package com.example.digitalact.Data.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.digitalact.Data.DataBase.Dao.ActDao
import com.example.digitalact.Data.DataBase.Dao.FullNameExecutorDao
import com.example.digitalact.Data.DataBase.Dao.InstallationLocationDao
import com.example.digitalact.Data.DataBase.Dao.TelephoneDao
import com.example.digitalact.Data.DataBase.Dao.TypeNewPUDao
import com.example.digitalact.Data.DataBase.Entity.ActEntity
import com.example.digitalact.Data.DataBase.Entity.FullNameExecutorEntity
import com.example.digitalact.Data.DataBase.Entity.InstallationLocationEntity
import com.example.digitalact.Data.DataBase.Entity.TaskEntity
import com.example.digitalact.Data.DataBase.Entity.TelephoneEntity
import com.example.digitalact.Data.DataBase.Entity.TypeNewPUEntity
import com.example.digitaltask.Data.DataBase.Dao.TaskDao

@Database(version = 1,
    entities = [
        TaskEntity::class,
        ActEntity::class,
        TelephoneEntity::class,
        TypeNewPUEntity::class,
        InstallationLocationEntity::class,
        FullNameExecutorEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTaskDao() : TaskDao
    abstract fun getActDao() : ActDao
    abstract fun getTelephoneDao() : TelephoneDao
    abstract fun getTypeNewPUDao() : TypeNewPUDao
    abstract fun getInstallationLocationDao() : InstallationLocationDao
    abstract fun getFullNameExecutorDao() : FullNameExecutorDao

}