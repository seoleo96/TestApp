package com.example.testapp.di

import com.example.testapp.ui.ContactCommunication
import com.example.testapp.ui.Dispatchers
import com.example.testapp.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    factory<ContactCommunication> { ContactCommunication.Base() }
    factory<Dispatchers> { Dispatchers.Base() }

    viewModel<HomeViewModel> {
        HomeViewModel(
            interactor = get(),
            dispatchers = get(),
            contactCommunication = get()
        )
    }
}