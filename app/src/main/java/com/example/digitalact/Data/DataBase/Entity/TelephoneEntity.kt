package com.example.digitalact.Data.DataBase.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "telephone")
data class TelephoneEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val numberTelephone: String,
    @ColumnInfo val iccID: String
)