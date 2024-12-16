package com.example.digitalact.Data.DataBase.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "typeNewPU")
data class TypeNewPUEntity (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val title: String
)
