package com.example.testapp.domain.repository

import com.example.testapp.domain.model.ContactDataModel
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {

    fun fetchContacts(): Flow<List<ContactDataModel>>
    suspend fun insertContacts()
    suspend fun deleteContact(id: Int)
    fun startTimer()
    fun cancelTimer()
}