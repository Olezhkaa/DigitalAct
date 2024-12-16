package com.example.digitalact.Data.DataBase.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fullNameExecutor")
data class FullNameExecutorEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val title: String
)