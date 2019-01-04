package com.tmt.tmtmachinetest.feature.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tmt.tmtmachinetest.R
import com.tmt.tmtmachinetest.pojo.ContactEntity

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val contacts: ArrayList<ContactEntity> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ContactViewHolder {
        return ContactViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.contact_item, p0, false))
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(p0: ContactViewHolder, p1: Int) {
        p0.nameText.text = contacts[p1].name
        p0.mobileNumberText.text = contacts[p1].mobileNumber
    }


    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactIv: ImageView = itemView.findViewById(R.id.contactIv)
        val nameText: TextView = itemView.findViewById(R.id.nameText)
        val mobileNumberText: TextView = itemView.findViewById(R.id.mobileNumberText)
    }

    fun update(contacts: ArrayList<ContactEntity>) {
        if (this.contacts.isNotEmpty()) this.contacts.clear()
        this.contacts.addAll(contacts)
        notifyDataSetChanged()
    }
}