package com.example.digitalact.Data.DataSource

import android.net.Uri
import com.example.digitalact.Data.DataBase.Entity.ActEntity
import com.example.digitalact.Domains.Model.Act


interface ExcelDataSource {
    fun readTaskFromExcel(fileUri: Uri)
    fun readTelephoneFromExcel(fileUri: Uri)
    fun writeActFromExcel(listActs: List<Act>, fileName: String)
}