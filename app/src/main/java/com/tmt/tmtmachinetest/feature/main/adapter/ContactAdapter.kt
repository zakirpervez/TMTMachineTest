package com.tmt.tmtmachinetest.feature.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.tmt.tmtmachinetest.R
import com.tmt.tmtmachinetest.pojo.ContactEntity
import com.tmt.tmtmachinetest.util.SMSCallback

class ContactAdapter(private val smsCallback: SMSCallback) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val contacts: ArrayList<ContactEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ContactViewHolder {
        return ContactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false))
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.nameText.text = contacts[position].name
        holder.mobileNumberText.text = contacts[position].mobileNumber
    }


    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactIv: ImageView = itemView.findViewById(R.id.contactIv)
        val nameText: TextView = itemView.findViewById(R.id.nameText)
        val mobileNumberText: TextView = itemView.findViewById(R.id.mobileNumberText)
        val sendSmsBt: Button = itemView.findViewById(R.id.sendSmsBt)
        val smsTextEd: EditText = itemView.findViewById(R.id.smsTextEd)

        init {
            sendSmsBt.setOnClickListener {
                val text: String = smsTextEd.text.toString().trim()
                val mobile = mobileNumberText.text.toString().trim()
                if (text.isNotEmpty()) {
                    smsCallback.sendSms(mobile, text)
                } else {
                    smsTextEd.error = itemView.context.getString(R.string.smsErrorMessage)
                }
            }
        }
    }

    fun update(contacts: ArrayList<ContactEntity>) {
        if (this.contacts.isNotEmpty()) this.contacts.clear()
        this.contacts.addAll(contacts)
        notifyDataSetChanged()
    }
}