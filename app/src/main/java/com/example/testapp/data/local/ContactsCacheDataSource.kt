package com.example.testapp.data.local

import kotlinx.coroutines.flow.Flow
import java.util.*


interface ContactsCacheDataSource {

    fun fetchContacts(): Flow<List<ContactModel>>
    suspend fun insertContacts(contacts: List<SourceContactModel>)
    suspend fun deleteContacts()
    suspend fun deleteContact(id: Int)
    fun start()
    fun cancel()

    class Base(
        private val contactsDao: ContactsDao,
    ) : ContactsCacheDataSource {

        private lateinit var timer: Timer

        override fun fetchContacts() = contactsDao.fetchContacts()

        override suspend fun insertContacts(contacts: List<SourceContactModel>) {
            contactsDao.insertAllContacts(contacts)
            val data = contactsDao.fetchAllContacts().map {
                ContactModel(it.id, it.number)
            }
            contactsDao.insertAll(data)
        }

        override suspend fun deleteContacts() = contactsDao.deleteAll()

        override suspend fun deleteContact(id: Int) = contactsDao.deleteContact(id)

        override fun start() {
            val timerTask = object : TimerTask() {
                override fun run() {
                    val data = contactsDao.fetchContact()
                    if (data != null) {
                        contactsDao.insertContact(ContactModel(data.id, data.number))
                    }
                }
            }
            timer = Timer()
            timer.scheduleAtFixedRate(timerTask, 5000, 5000)
        }

        override fun cancel() {
            timer.cancel()
        }
    }
}