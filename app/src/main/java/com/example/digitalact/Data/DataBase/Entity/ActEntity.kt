package com.example.digitalact.Data.DataBase.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "acts")
data class ActEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo val accountNumber: String,
    @ColumnInfo val address: String,
    @ColumnInfo val fullName: String,
    @ColumnInfo val typeOldPU: String,
    @ColumnInfo val numberOldPU: String,
    @ColumnInfo val typeOldTT: String,
    @ColumnInfo val numberOldTT: String,
    @ColumnInfo val reasonReplacement: String,
    @ColumnInfo val numberApplication: String,
    @ColumnInfo val typeNewPU: String,
    @ColumnInfo val numberNewPU: String,
    @ColumnInfo val typeNewTT: String,
    @ColumnInfo val numberNewTT: String,
    @ColumnInfo val sealPUOne: String,
    @ColumnInfo val sealPUTwo: String,
    @ColumnInfo val sealPUThree: String,
    @ColumnInfo val simCard: String,
    @ColumnInfo val dateCompletion: String,
    @ColumnInfo val fullNameExecutor: String,
    @ColumnInfo val installationLocation: String,
    @ColumnInfo val comment: String
)