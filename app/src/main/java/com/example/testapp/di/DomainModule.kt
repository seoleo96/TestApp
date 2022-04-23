package com.example.testapp.di

import com.example.testapp.domain.interactor.ContactsInteractor
import org.koin.dsl.module

val domainModule = module {
    factory<ContactsInteractor> {
        ContactsInteractor.Base(contactsRepository = get())
    }
}