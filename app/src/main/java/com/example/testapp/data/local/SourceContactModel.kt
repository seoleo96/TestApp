package com.example.testapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_contacts")
data class SourceContactModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: String,
)
