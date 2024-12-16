package com.example.digitalact.Data.Repository

import android.content.Context
import android.net.Uri
import android.telephony.SmsManager
import android.widget.Toast
import com.example.digitalact.Data.DataBase.Dao.TelephoneDao
import com.example.digitalact.Data.DataSource.LocalExcelDataSource
import com.example.digitalact.Data.Mapper.TelephoneMapper
import com.example.digitalact.Domains.Model.Telephone
import com.example.digitalact.Domains.Repository.TelephoneRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TelephoneRepositoryImpl(private val telephoneDao: TelephoneDao) : TelephoneRepository {

    private val telephoneMapper = TelephoneMapper()

    override suspend fun insertTelephone(telephone: Telephone) {
        withContext(Dispatchers.IO) {
            telephoneDao.insertTelephoneData(telephoneMapper.mapToEntity(telephone))
        }
    }

    override suspend fun getAllTelephone(): List<Telephone> {
        return withContext(Dispatchers.IO) {
            return@withContext telephoneMapper.fromEntityList(telephoneDao.getAllTelephoneData())
        }
    }

    override suspend fun getNumberByICC(iccID: String): String {
        return withContext(Dispatchers.IO) {
            return@withContext telephoneDao.getNumberTelephoneByICC(iccID)
        }
    }

    override suspend fun deleteAllTelephone() {
        withContext(Dispatchers.IO) {
            telephoneDao.deleteAllTelephoneData()
        }
    }

    fun loadTelephoneData(fileUri: Uri, context: Context?) {
        val excelDataSource = LocalExcelDataSource(context!!)
        excelDataSource.readTelephoneFromExcel(fileUri)
    }

    fun sendMessage(phoneNumber: String, context: Context) {
        val code = (1000..9999).random()  // Генерация 4-значного кода
        val message = "Ваш код подтверждения: $code"

        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(context, "SMS отправлено на номер: $phoneNumber", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Ошибка отправки SMS: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}