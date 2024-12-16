package com.example.digitalact

import android.content.Context
import androidx.room.Room
import com.example.digitalact.Data.DataBase.AppDatabase
import com.example.digitalact.Data.Repository.ActRepositoryImpl
import com.example.digitalact.Data.Repository.FullNameExecutorRepositoryImpl
import com.example.digitalact.Data.Repository.InstallationLocationRepositoryImpl
import com.example.digitalact.Data.Repository.TaskRepositoryImpl
import com.example.digitalact.Data.Repository.TelephoneRepositoryImpl
import com.example.digitalact.Data.Repository.TypeNewPURepositoryImpl

object Dependencies {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "DigitalActDB.db")
            .build()
    }

    val taskRepository: TaskRepositoryImpl by lazy { TaskRepositoryImpl(appDatabase.getTaskDao()) }
    val actRepository: ActRepositoryImpl by lazy { ActRepositoryImpl(appDatabase.getActDao()) }
    val telephoneRepository: TelephoneRepositoryImpl by lazy { TelephoneRepositoryImpl(appDatabase.getTelephoneDao()) }
    val typeNewPURepository: TypeNewPURepositoryImpl by lazy { TypeNewPURepositoryImpl(appDatabase.getTypeNewPUDao()) }
    val installationLocationRepository: InstallationLocationRepositoryImpl by lazy { InstallationLocationRepositoryImpl(appDatabase.getInstallationLocationDao()) }
    val fullNameExecutorRepository: FullNameExecutorRepositoryImpl by lazy { FullNameExecutorRepositoryImpl(appDatabase.getFullNameExecutorDao()) }


}