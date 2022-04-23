package com.example.testapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.ContactItemBinding
import com.example.testapp.domain.model.ContactDataModel

class ContactsAdapter(private val deleteOnClick: (Int) -> Unit) :
    RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    private var contactsList = listOf<ContactDataModel>()
    fun updateList(list: List<ContactDataModel>) {
        val diffUtil = ContactsDiffUtil(contactsList, list)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        contactsList = list
        diffResults.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ContactViewHolder {
        return ContactViewHolder(makeView(parent), deleteOnClick)
    }

    private fun makeView(parent: ViewGroup): ContactItemBinding =
        ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val bind = contactsList[position]
        holder.bind(bind)

    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    class ContactViewHolder(
        private val binding: ContactItemBinding,
        private val deleteOnClick: (Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: ContactDataModel) {
            binding.contact.text = contact.number

            binding.delete.setOnClickListener { deleteOnClick.invoke(contact.id) }
        }
    }
}