package com.example.testapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllContacts(contacts: List<SourceContactModel>)

    @Query("SELECT * FROM source_contacts ORDER BY RANDOM() LIMIT 1")
    fun fetchContact(): SourceContactModel

    @Query("SELECT * FROM source_contacts WHERE id>0 LIMIT 15")
    suspend fun fetchAllContacts(): List<SourceContactModel>

    @Query("SELECT * FROM contacts ORDER BY id ASC")
    fun fetchContacts(): Flow<List<ContactModel>>

    @Query("DELETE FROM contacts")
    suspend fun deleteAll()

    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun deleteContact(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contacts: List<ContactModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: ContactModel)
}