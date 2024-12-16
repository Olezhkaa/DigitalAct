package com.example.digitalact.Domains.Repository

import android.content.Context
import android.net.Uri
import com.example.digitalact.Domains.Model.Task

interface TaskRepository {
    fun loadTask(fileUri: Uri, context: Context?)
    suspend fun insertTaskData(task: Task)
    suspend fun getAllTask() : List<*>
    suspend fun deleteAllTask()
    suspend fun deleteTaskData(accountNumber: String)
    suspend fun getTaskById(accountNumber: String) : Task


}