package com.example.digitalact.Data.Repository

import com.example.digitalact.Data.DataBase.Dao.TypeNewPUDao
import com.example.digitalact.Data.DataBase.Entity.TypeNewPUEntity
import com.example.digitalact.Data.Mapper.TypeNewPUMapper
import com.example.digitalact.Domains.Model.SpinnerData
import com.example.digitalact.Domains.Repository.SpinnerDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TypeNewPURepositoryImpl(private val typeNewPUDao: TypeNewPUDao) : SpinnerDataRepository {

    private val typeNewPUMapper = TypeNewPUMapper()
    
    override suspend fun insertSpinnerData(spinnerData: SpinnerData) {
        withContext(Dispatchers.IO) {
            typeNewPUDao.insertNewTypeNewPUData(typeNewPUMapper.mapToEntity(spinnerData))
        }
    }

    private suspend fun getAllTypeNewPUData(): List<TypeNewPUEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext typeNewPUDao.getAllTypeNewPUData()
        }
    }

    override suspend  fun getAllSpinnerData(): List<String> {
        val listTypeNewPUEntity = typeNewPUMapper.fromEntityList(getAllTypeNewPUData())
        val listString: MutableList<String> = mutableListOf()
        for(i in listTypeNewPUEntity)
            listString.add(i.title)
        return listString
    }

    override suspend fun deleteSpinnerData(name: String) {
        withContext(Dispatchers.IO) {
            typeNewPUDao.deleteTypeNewPUDataById(getId(name))
        }
    }


    private suspend fun getId(title: String) : Long {
        var id: Long = 0
        for(i in getAllTypeNewPUData()) {
            if(title == i.title) {
                id = i.id
                break
            }
        }
        return id
    }
}