package com.example.testapp.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.interactor.ContactsInteractor
import com.example.testapp.domain.model.ContactDataModel
import kotlinx.coroutines.flow.collect

class HomeViewModel(
    private val interactor: ContactsInteractor,
    private val dispatchers: com.example.testapp.ui.Dispatchers,
    private val contactCommunication: ContactCommunication,
) : ViewModel() {

    fun fetchList() {
        dispatchers.launchIO(viewModelScope) {
            interactor.fetchContacts().collect { list ->
                dispatchers.launchUI(viewModelScope) {
                    contactCommunication.map(list)
                }
            }
        }
    }

    fun successObserve(lifecycleOwner: LifecycleOwner, observer: Observer<List<ContactDataModel>>) {
        contactCommunication.observe(lifecycleOwner, observer)
    }

    fun insertContacts() {
        dispatchers.launchIO(viewModelScope) {
            interactor.insertContacts()
        }
    }

    fun deleteContact(id: Int) {
        dispatchers.launchIO(viewModelScope) {
            interactor.deleteContact(id)
        }
    }

    fun startTimer() = interactor.startTimer()

    fun cancelTimer() = interactor.cancelTimer()
}