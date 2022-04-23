package com.example.testapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testapp.core.Mapper
import com.example.testapp.domain.model.ContactDataModel

@Entity(tableName = "contacts")
data class ContactModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val number: String,
) : Mapper<ContactDataModel> {
    override fun map(): ContactDataModel {
        return ContactDataModel(id, number)
    }
}