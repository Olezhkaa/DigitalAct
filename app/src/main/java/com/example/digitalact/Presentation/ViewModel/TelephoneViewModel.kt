package com.example.digitalact.Presentation.ViewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalact.Data.Repository.TelephoneRepositoryImpl
import com.example.digitalact.Domains.Model.Telephone
import kotlinx.coroutines.launch

class TelephoneViewModel(private val telephoneRepository: TelephoneRepositoryImpl) : ViewModel() {

    private val _telephoneList = MutableLiveData<List<Telephone>>()
    val telephoneList: LiveData<List<Telephone>> = _telephoneList

    private val _getNumberTelephone = MutableLiveData<String>()
    val getNumberTelephone: LiveData<String> = _getNumberTelephone

    init {
        getAllTelephoneData()
    }

    fun insertTelephoneData(numberTelephone: String, iccID: String) {
        viewModelScope.launch {
            telephoneRepository.insertTelephone(Telephone(numberTelephone, iccID))
        }
    }

    fun getAllTelephoneData() {
        viewModelScope.launch {
            _telephoneList.value = telephoneRepository.getAllTelephone()
        }
    }

    fun getNumberTelephoneByICC(iccID: String) {
        viewModelScope.launch {
            _getNumberTelephone.value = telephoneRepository.getNumberByICC(iccID)
        }
    }

    fun deleteAllTelephoneData() {
        viewModelScope.launch {
            telephoneRepository.deleteAllTelephone()
        }
    }

    fun loadTelephoneData(fileUri: Uri, context: Context?) {
        telephoneRepository.loadTelephoneData(fileUri, context)
    }

    fun sendMessageCode(phoneNumber: String, context: Context) {
        telephoneRepository.sendMessage(phoneNumber, context)
    }
}