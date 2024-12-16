package com.example.digitalact.Domains.Repository

import com.example.digitalact.Domains.Model.Telephone

interface TelephoneRepository {
    suspend fun insertTelephone(telephone: Telephone)
    suspend fun getAllTelephone() : List<Telephone>
    suspend fun getNumberByICC(iccID: String) : String
    suspend fun deleteAllTelephone()
}