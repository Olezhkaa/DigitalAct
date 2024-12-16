package com.example.digitalact.Domains.Repository

import com.example.digitalact.Domains.Model.SpinnerData

interface SpinnerDataRepository {
    suspend fun insertSpinnerData(spinnerData: SpinnerData)
    suspend fun getAllSpinnerData() : List<String>
    suspend fun deleteSpinnerData(name: String)
}