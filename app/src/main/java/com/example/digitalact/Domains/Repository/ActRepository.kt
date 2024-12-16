package com.example.digitalact.Domains.Repository

import android.content.Context
import com.example.digitalact.Domains.Model.Act

interface ActRepository {
    suspend fun insertActData(act: Act)
    suspend fun getAllAct() : List<Act>
    suspend fun deleteAllAct()
    suspend fun deleteActData(accountNumber: String)
    suspend fun getActById(accountNumber: String) : Act
    suspend fun updateActData(act: Act)

    fun saveAct(listAct: List<Act>, fileName: String, context: Context)
    fun shareAct(fileName: String, context: Context)
}