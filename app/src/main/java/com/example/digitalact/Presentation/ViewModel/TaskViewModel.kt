package com.example.digitalact.Presentation.ViewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalact.Data.Repository.TaskRepositoryImpl
import com.example.digitalact.Domains.Model.Task
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepository: TaskRepositoryImpl) : ViewModel() {

    private val _currentTask = MutableLiveData<Task>()
    val currentTask: LiveData<Task> = _currentTask

    private val _allTask = MutableLiveData<List<Task>>()
    val allTask: LiveData<List<Task>> = _allTask

    init {
        getAllTaskDataFromDatabase()
    }

    fun insertNewTaskDataInDatabase(accountNumber: String, address: String, fullName: String, typePU: String, numberPU: String, reasonReplacement: String
    ,numberApplication: String,telephone: String,comment: String) {
        viewModelScope.launch {
            val newTask = Task(accountNumber, address, fullName, typePU, numberPU, reasonReplacement, numberApplication, telephone, comment)
            taskRepository.insertTaskData(newTask)
        }
    }

    fun getAllTaskDataFromDatabase(){
        viewModelScope.launch  {
            _allTask.value = taskRepository.getAllTask()
        }
    }

    fun getTaskDataByAccountNumber(accountNumber: String){
        viewModelScope.launch {
            _currentTask.value = taskRepository.getTaskById(accountNumber)
        }
    }

    fun deleteTaskDataByAccountNumber(accountNumber: String) {
        viewModelScope.launch {
            taskRepository.deleteTaskData(accountNumber)
        }
    }

    fun deleteAllTaskData() {
        viewModelScope.launch {
            taskRepository.deleteAllTask()
        }
    }

    fun loadTaskData(fileUri: Uri, context: Context?) {
        taskRepository.loadTask(fileUri, context)
    }

}