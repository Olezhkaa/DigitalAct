package com.example.digitalact.Presentation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalact.Data.Mapper.InstallationLocationMapper
import com.example.digitalact.Data.Repository.FullNameExecutorRepositoryImpl
import com.example.digitalact.Data.Repository.InstallationLocationRepositoryImpl
import com.example.digitalact.Data.Repository.TypeNewPURepositoryImpl
import com.example.digitalact.Domains.Model.SpinnerData
import kotlinx.coroutines.launch

abstract class SpinnerViewModel<T> : ViewModel() {

    protected val _dataList = MutableLiveData<T>()
    val dataList: LiveData<T> = _dataList

    abstract fun getAllData()
    abstract fun insertSpinnerData(title: String)
    abstract fun deleteSpinnerData(title: String)

}

class TypeNewPUViewModel(private val typeNewPURepository: TypeNewPURepositoryImpl) : SpinnerViewModel<List<String>>() {

    init {
        getAllData()
    }

    override fun getAllData() {
        viewModelScope.launch {
            _dataList.value = typeNewPURepository.getAllSpinnerData()
        }
    }

    override fun insertSpinnerData(title: String) {
        viewModelScope.launch {
            typeNewPURepository.insertSpinnerData(SpinnerData(title))
        }
    }

    override fun deleteSpinnerData(title: String) {
        viewModelScope.launch {
            typeNewPURepository.deleteSpinnerData(title)
        }
    }
}

class InstallationLocationViewModel(private val installationLocationRepository: InstallationLocationRepositoryImpl) : SpinnerViewModel<List<String>>() {

    init {
        getAllData()
    }

    override fun getAllData() {
        viewModelScope.launch {
            _dataList.value = installationLocationRepository.getAllSpinnerData()
        }
    }

    override fun insertSpinnerData(title: String) {
        viewModelScope.launch {
            installationLocationRepository.insertSpinnerData(SpinnerData(title))
        }
    }

    override fun deleteSpinnerData(title: String) {
        viewModelScope.launch {
            installationLocationRepository.deleteSpinnerData(title)
        }
    }
}

class FullNameExecutorViewModel(private val fullNameExecutorRepository: FullNameExecutorRepositoryImpl) : SpinnerViewModel<List<String>>() {

    init {
        getAllData()
    }

    override fun getAllData() {
        viewModelScope.launch {
            _dataList.value = fullNameExecutorRepository.getAllSpinnerData()
        }
    }

    override fun insertSpinnerData(title: String) {
        viewModelScope.launch {
            fullNameExecutorRepository.insertSpinnerData(SpinnerData(title))
        }
    }

    override fun deleteSpinnerData(title: String) {
        viewModelScope.launch {
            fullNameExecutorRepository.deleteSpinnerData(title)
        }
    }
}