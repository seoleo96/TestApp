package com.example.testapp.di

import com.example.testapp.data.cachenumbers.ContactsDataSource
import com.example.testapp.data.local.ContactsCacheDataSource
import com.example.testapp.data.local.ContactsDB
import com.example.testapp.data.local.ContactsDao
import com.example.testapp.data.repository.BaseContactsRepository
import com.example.testapp.domain.repository.ContactsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    fun getDao(db: ContactsDB): ContactsDao {
        return db.contactsDao()
    }

    single<ContactsDB> {
        ContactsDB.getDatabase(context = androidContext())
    }

    factory<ContactsDao> {
        getDao(db = get())
    }

    factory<ContactsCacheDataSource> {
        ContactsCacheDataSource.Base(contactsDao = get())
    }

    factory<ContactsDataSource> {
        ContactsDataSource.Base(context = androidContext())
    }

    factory<ContactsRepository> {
        BaseContactsRepository(cacheDataSource = get(), contactsDataSource = get())
    }
}