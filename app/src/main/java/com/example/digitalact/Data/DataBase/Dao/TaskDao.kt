package com.example.digitaltask.Data.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.digitalact.Data.DataBase.Entity.TaskEntity
import com.example.digitalact.Domains.Model.Task
@Dao
interface TaskDao {
    @Insert(entity = TaskEntity::class)
    fun insertNewTaskData(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getAllTaskData(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :idTask")
    fun getTaskDataByID(idTask: Long): TaskEntity

    @Query("DELETE FROM tasks WHERE id = :idTask")
    fun deleteTaskDataById(idTask: Long)

    @Query("DELETE FROM tasks")
    fun deleteAllTaskData()
}