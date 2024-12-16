package com.example.digitalact.Data.Repository

import com.example.digitalact.Data.DataBase.Dao.FullNameExecutorDao
import com.example.digitalact.Data.DataBase.Entity.FullNameExecutorEntity
import com.example.digitalact.Data.Mapper.FullNameExecutorMapper
import com.example.digitalact.Domains.Model.SpinnerData
import com.example.digitalact.Domains.Repository.SpinnerDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FullNameExecutorRepositoryImpl(private val fullNameExecutorDao: FullNameExecutorDao) :
    SpinnerDataRepository {

    private val fullNameExecutorMapper = FullNameExecutorMapper()

    override suspend fun insertSpinnerData(spinnerData: SpinnerData) {
        withContext(Dispatchers.IO) {
            fullNameExecutorDao.insertNewFullNameExecutorData(fullNameExecutorMapper.mapToEntity(spinnerData))
        }
    }

    private suspend fun getAllFullNameExecutorData(): List<FullNameExecutorEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext fullNameExecutorDao.getAllFullNameExecutorData()
        }
    }

    override suspend  fun getAllSpinnerData(): List<String> {
        val listFullNameExecutorEntity = fullNameExecutorMapper.fromEntityList(getAllFullNameExecutorData())
        val listString: MutableList<String> = mutableListOf()
        for(i in listFullNameExecutorEntity)
            listString.add(i.title)
        return listString
    }

    override suspend fun deleteSpinnerData(name: String) {
        withContext(Dispatchers.IO) {
            fullNameExecutorDao.deleteFullNameExecutorDataById(getId(name))
        }
    }


    private suspend fun getId(title: String) : Long {
        var id: Long = 0
        for(i in getAllFullNameExecutorData()) {
            if(title == i.title) {
                id = i.id
                break
            }
        }
        return id
    }
}