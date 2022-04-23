package com.example.testapp.ui

import com.example.testapp.domain.model.ContactDataModel

interface ContactCommunication : Communication<List<ContactDataModel>> {
    class Base : Communication.Base<List<ContactDataModel>>(), ContactCommunication
}