package com.example.testapp.data.cachenumbers

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.example.testapp.data.local.SourceContactModel

interface ContactsDataSource {

    suspend fun fetchContacts(): List<SourceContactModel>

    class Base(private val context: Context) : ContactsDataSource {
        override suspend fun fetchContacts(): List<SourceContactModel> {
            val contactsNumber = mutableListOf<SourceContactModel>()
            val phoneCursor: Cursor? = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            if (phoneCursor != null && phoneCursor.count > 0) {
                val numberIndex =
                    phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                while (phoneCursor.moveToNext()) {
                    val number = phoneCursor.getString(numberIndex)
                    if (number.startsWith("+7")) {
                        val contact = SourceContactModel(0, number)
                        if (!contactsNumber.contains(contact)) {
                            contactsNumber.add(contact)
                        }
                    }
                }
                phoneCursor.close()
            }
            return contactsNumber
        }
    }
}