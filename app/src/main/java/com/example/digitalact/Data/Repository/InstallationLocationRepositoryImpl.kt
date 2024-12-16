package com.example.digitalact.Data.Repository

import com.example.digitalact.Data.DataBase.Dao.InstallationLocationDao
import com.example.digitalact.Data.DataBase.Entity.InstallationLocationEntity
import com.example.digitalact.Data.Mapper.InstallationLocationMapper
import com.example.digitalact.Domains.Model.SpinnerData
import com.example.digitalact.Domains.Repository.SpinnerDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InstallationLocationRepositoryImpl(private val installationLocationDao: InstallationLocationDao) :
    SpinnerDataRepository {

    private val installationLocationMapper = InstallationLocationMapper()

    override suspend fun insertSpinnerData(spinnerData: SpinnerData) {
        withContext(Dispatchers.IO) {
            installationLocationDao.insertNewInstallationLocationData(installationLocationMapper.mapToEntity(spinnerData))
        }
    }

    private suspend fun getAllInstallationLocationData(): List<InstallationLocationEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext installationLocationDao.getAllInstallationLocationData()
        }
    }

    override suspend  fun getAllSpinnerData(): List<String> {
        val listInstallationLocationEntity = installationLocationMapper.fromEntityList(getAllInstallationLocationData())
        val listString: MutableList<String> = mutableListOf()
        for(i in listInstallationLocationEntity)
            listString.add(i.title)
        return listString
    }

    override suspend fun deleteSpinnerData(name: String) {
        withContext(Dispatchers.IO) {
            installationLocationDao.deleteInstallationLocationDataById(getId(name))
        }
    }


    private suspend fun getId(title: String) : Long {
        var id: Long = 0
        for(i in getAllInstallationLocationData()) {
            if(title == i.title) {
                id = i.id
                break
            }
        }
        return id
    }
}