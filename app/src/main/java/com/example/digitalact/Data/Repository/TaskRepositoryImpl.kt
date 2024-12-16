package com.example.digitalact.Data.Repository

import android.content.Context
import android.net.Uri
import com.example.digitalact.Data.DataBase.Entity.TaskEntity
import com.example.digitalact.Data.DataSource.LocalExcelDataSource
import com.example.digitalact.Data.Mapper.TaskMapper
import com.example.digitalact.Domains.Model.Task
import com.example.digitalact.Domains.Repository.TaskRepository
import com.example.digitaltask.Data.DataBase.Dao.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    private val taskMapper = TaskMapper()

    private suspend fun getAllTaskData(): List<TaskEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext taskDao.getAllTaskData()
        }
    }

    override suspend fun insertTaskData(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insertNewTaskData(taskMapper.mapToEntity(task))
        }
    }

    override suspend fun getAllTask(): List<Task> {
        return taskMapper.fromEntityList(getAllTaskData())
    }

    override suspend fun deleteAllTask() {
        withContext(Dispatchers.IO) {
            taskDao.deleteAllTaskData()
        }
    }

    override suspend fun deleteTaskData(accountNumber: String) {
        withContext(Dispatchers.IO) {
            taskDao.deleteTaskDataById(getId(accountNumber))
        }
    }

    override suspend fun getTaskById(accountNumber: String) : Task {
        return withContext(Dispatchers.IO) {
            return@withContext taskMapper.mapFromEntity(taskDao.getTaskDataByID(getId(accountNumber)))
        }
    }

    override fun loadTask(fileUri: Uri, context: Context?) {
        val excelDataSource = LocalExcelDataSource(context!!)
        excelDataSource.readTaskFromExcel(fileUri)
    }

    private suspend fun getId(accountNumber: String) : Long {
        var id: Long = 0
        for(i in getAllTaskData()) {
            if(accountNumber == i.accountNumber) {
                id = i.id
                break
            }
        }
        return id
    }
}