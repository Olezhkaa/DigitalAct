package com.example.digitalact.Data.DataBase.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "installationLocation")
data class InstallationLocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val title: String
)