package com.example.testapp.data.repository

import com.example.testapp.data.cachenumbers.ContactsDataSource
import com.example.testapp.data.local.ContactsCacheDataSource
import com.example.testapp.data.local.SourceContactModel
import com.example.testapp.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.map

class BaseContactsRepository(
    private val cacheDataSource: ContactsCacheDataSource,
    private val contactsDataSource: ContactsDataSource,

    ) : ContactsRepository {

    override fun fetchContacts() = cacheDataSource.fetchContacts().map { list ->
        list.map { contactModel ->
            contactModel.map()
        }
    }

    override suspend fun insertContacts() {
        val contacts: List<SourceContactModel> = contactsDataSource.fetchContacts()
        cacheDataSource.insertContacts(contacts)
    }

    override suspend fun deleteContact(id: Int) = cacheDataSource.deleteContact(id)

    override fun startTimer() = cacheDataSource.start()

    override fun cancelTimer() = cacheDataSource.cancel()
}