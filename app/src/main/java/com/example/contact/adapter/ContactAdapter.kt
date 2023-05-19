package com.example.contact.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.databinding.ContactItemBinding
import com.example.contact.model.Contact

class ContactAdapter(var list: ArrayList<Contact>, var contactInterface: ContactAdapter.ContactInterface) : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {
    class ContactHolder(binding:ContactItemBinding):
        RecyclerView.ViewHolder(binding.root){
        var name = binding.contactName
        var number = binding.contactNumber
        var call = binding.callImg
        var main = binding.contactItemMain

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(ContactItemBinding.
        inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.name.text = list[position].name
        holder.number.text = list[position].number

        holder.main.setOnClickListener {
            contactInterface.onClick(list[position])
        }



        holder.call.setOnClickListener {
            val phone_intent = Intent(Intent.ACTION_CALL)

            phone_intent.data = Uri.parse("tel:${list[list.size].number}")
        }
    }

    interface ContactInterface{
        fun onClick(contact: Contact)
    }
}