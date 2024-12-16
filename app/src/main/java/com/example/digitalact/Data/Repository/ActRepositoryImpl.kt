package com.example.digitalact.Data.Repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.example.digitalact.Data.DataBase.Dao.ActDao
import com.example.digitalact.Data.DataBase.Entity.ActEntity
import com.example.digitalact.Data.DataBase.Entity.TaskEntity
import com.example.digitalact.Data.DataSource.LocalExcelDataSource
import com.example.digitalact.Data.Mapper.ActMapper
import com.example.digitalact.Data.Mapper.TaskMapper
import com.example.digitalact.Domains.Model.Act
import com.example.digitalact.Domains.Model.Task
import com.example.digitalact.Domains.Repository.ActRepository
import com.example.digitaltask.Data.DataBase.Dao.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ActRepositoryImpl(private val actDao: ActDao) : ActRepository {

    private val actMapper = ActMapper()


    override suspend fun insertActData(act: Act) {
        withContext(Dispatchers.IO) {
            actDao.insertNewActData(actMapper.mapToEntity(act))
        }
    }

    private suspend fun getAllActData(): List<ActEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext actDao.getAllActData()
        }
    }

    override suspend fun getAllAct(): List<Act> {
        return actMapper.fromEntityList(getAllActData())
    }

    override suspend fun deleteAllAct() {
        withContext(Dispatchers.IO) {
            actDao.deleteAllActData()
        }
    }

    override suspend fun deleteActData(accountNumber: String) {
        withContext(Dispatchers.IO) {
            actDao.deleteActDataById(getId(accountNumber))
        }
    }

    override suspend fun updateActData(act: Act) {
        withContext(Dispatchers.IO) {
            val actEntity = actMapper.mapToEntity(act)
            actEntity.id = getId(act.accountNumber)
            actDao.updateActData(actEntity)
        }
    }

     override suspend fun getActById(accountNumber: String) : Act {
        return withContext(Dispatchers.IO) {
            return@withContext actMapper.mapFromEntity(actDao.getActDataByID(getId(accountNumber)))
        }
    }

    override fun saveAct(listAct: List<Act>, fileName: String, context: Context) {
        val localExcelDataSource = LocalExcelDataSource(context)
        localExcelDataSource.writeActFromExcel(listAct, fileName)
    }

    override fun shareAct(fileName: String, context: Context) {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "$fileName.xlsx")

        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Получение URI для файла, сохраненного через MediaStore
            val projection = arrayOf(MediaStore.MediaColumns._ID)
            val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
            val selectionArgs = arrayOf("$fileName.xlsx")

            val query = context.contentResolver.query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                selectionArgs,
                null
            )

            query?.use {
                if (it.moveToFirst()) {
                    val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                    Uri.withAppendedPath(MediaStore.Files.getContentUri("external"), id.toString())
                } else {
                    null
                }
            }
        } else {
            // Получение URI для файла, сохраненного в обычной файловой системе
            FileProvider.getUriForFile(context,  context.packageName + ".fileprovider", file)
        }

        uri?.let {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                putExtra(Intent.EXTRA_STREAM, it)
            }

            context.startActivity(Intent.createChooser(shareIntent, "Share Excel file"))
        }
    }

    private suspend fun getId(accountNumber: String) : Long {
        var id: Long = 0
        for(i in getAllActData()) {
            if(accountNumber == i.accountNumber) {
                id = i.id
                break
            }
        }
        return id
    }
}