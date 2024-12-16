package com.example.digitalact.Data.DataBase.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val accountNumber: String,
    @ColumnInfo val address: String,
    @ColumnInfo val fullName: String,
    @ColumnInfo val typePU: String,
    @ColumnInfo val numberPU: String,
    @ColumnInfo val reasonReplacement: String,
    @ColumnInfo val numberApplication: String,
    @ColumnInfo val telephone: String,
    @ColumnInfo val comment: String
)