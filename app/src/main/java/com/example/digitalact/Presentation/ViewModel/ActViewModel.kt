package com.example.digitalact.Presentation.ViewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalact.Data.Repository.ActRepositoryImpl
import com.example.digitalact.Domains.Model.Act
import kotlinx.coroutines.launch

class ActViewModel(private val actRepository: ActRepositoryImpl) : ViewModel() {

    private val _currentAct = MutableLiveData<Act>()
    val currentAct: LiveData<Act> = _currentAct

    private val _allAct = MutableLiveData<List<Act>>()
    val allAct: LiveData<List<Act>> = _allAct

    init {
        getAllActDataFromDatabase()
    }

    fun insertNewActDataInDatabase(accountNumber: String, address: String, fullName: String, typeOldPU: String, numberOldPU: String, typeOldTT: String
                                    ,numberOldTT: String,reasonReplacement: String,numberApplication: String,typeNewPU: String,numberNewPU: String,typeNewTT: String,numberNewTT: String,
                                   sealPUOne: String,sealPUTwo: String,sealPUThree: String,simCard: String,dateCompletion: String,fullNameExecutor: String,installationLocation: String,comment: String) {
        viewModelScope.launch {
            val newAct = Act(accountNumber, address, fullName, typeOldPU, numberOldPU, typeOldTT
                ,numberOldTT,reasonReplacement,numberApplication,typeNewPU,numberNewPU,typeNewTT,numberNewTT,
                sealPUOne,sealPUTwo,sealPUThree,simCard,dateCompletion,fullNameExecutor,installationLocation,comment)
            actRepository.insertActData(newAct)
        }
    }

    fun getAllActDataFromDatabase(){
        viewModelScope.launch  {
            _allAct.value = actRepository.getAllAct()
        }
    }

    fun getActDataByAccountNumber(accountNumber: String){
        viewModelScope.launch {
            _currentAct.value = actRepository.getActById(accountNumber)
        }
    }

    fun deleteActDataByAccountNumber(accountNumber: String) {
        viewModelScope.launch {
            actRepository.deleteActData(accountNumber)
        }
    }

    fun deleteAllActData() {
        viewModelScope.launch {
            actRepository.deleteAllAct()
        }
    }

    fun updateActData(accountNumber: String, address: String, fullName: String, typeOldPU: String, numberOldPU: String, typeOldTT: String
                      ,numberOldTT: String,reasonReplacement: String,numberApplication: String,typeNewPU: String,numberNewPU: String,typeNewTT: String,numberNewTT: String,
                      sealPUOne: String,sealPUTwo: String,sealPUThree: String,simCard: String,dateCompletion: String,fullNameExecutor: String,installationLocation: String,comment: String) {
        viewModelScope.launch {
            val newAct = Act(accountNumber, address, fullName, typeOldPU, numberOldPU, typeOldTT
                ,numberOldTT,reasonReplacement,numberApplication,typeNewPU,numberNewPU,typeNewTT,numberNewTT,
                sealPUOne,sealPUTwo,sealPUThree,simCard,dateCompletion,fullNameExecutor,installationLocation,comment)
            actRepository.updateActData(newAct)
        }
    }

    fun saveActData(listAct: List<Act>, fileName: String, context: Context?) {
        actRepository.saveAct(listAct, fileName, context!!)
    }

    fun shareActData(fileName: String, context: Context?) {
        actRepository.shareAct(fileName, context!!)
    }

}