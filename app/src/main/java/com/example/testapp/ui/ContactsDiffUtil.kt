package com.example.testapp.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.testapp.domain.model.ContactDataModel

class ContactsDiffUtil(
    private val oldList: List<ContactDataModel>,
    private val newList: List<ContactDataModel>,
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = when {
        oldList[oldItemPosition].id != newList[newItemPosition].id -> false
        oldList[oldItemPosition].number != newList[newItemPosition].number -> false
        else -> true
    }
}