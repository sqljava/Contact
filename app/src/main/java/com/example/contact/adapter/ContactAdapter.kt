package com.example.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.databinding.ContactItemBinding
import com.example.contact.model.Contact

class ContactAdapter(var list: ArrayList<Contact>) : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {
    class ContactHolder(binding:ContactItemBinding):
        RecyclerView.ViewHolder(binding.root){
        var name = binding.contactName
        var number = binding.contactNumber

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

    }
}