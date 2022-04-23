package com.example.testapp.domain.interactor

import com.example.testapp.domain.model.ContactDataModel
import com.example.testapp.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ContactsInteractor {

    fun fetchContacts(): Flow<List<ContactDataModel>>
    suspend fun insertContacts()
    suspend fun deleteContact(id: Int)
    fun startTimer()
    fun cancelTimer()

    class Base(
        private val contactsRepository: ContactsRepository,
    ) : ContactsInteractor {
        override fun fetchContacts(): Flow<List<ContactDataModel>> {
            val data = contactsRepository.fetchContacts()
            val filteredData = data.map { list ->
                list.filter { it.number.startsWith("+7") }.map {
                    ContactDataModel(it.id, it.number.replace("\\s".toRegex(), ""))
                }
            }
            return filteredData
        }

        override suspend fun insertContacts() = contactsRepository.insertContacts()

        override suspend fun deleteContact(id: Int) = contactsRepository.deleteContact(id)

        override fun startTimer() = contactsRepository.startTimer()

        override fun cancelTimer() = contactsRepository.cancelTimer()
    }
}