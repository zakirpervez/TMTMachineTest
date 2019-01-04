package com.tmt.tmtmachinetest.util

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.tmt.tmtmachinetest.pojo.ContactEntity

const val ONE_SEC: Long = 1000


interface ContactCallback {
    fun contactFetch(contacts: ArrayList<ContactEntity>)
}

open class ContactUtility(private val from: Activity, private val callback: ContactCallback) : LifecycleObserver {

    private val contentUri = ContactsContract.Contacts.CONTENT_URI
    private val phoneContentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    private lateinit var cursor: Cursor
    private val contacts: ArrayList<ContactEntity> = ArrayList()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchContact() {
        cursor = from.contentResolver.query(
            contentUri,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC "
        )!!

        if (cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val _id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name: String = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val mobileCursor = from.contentResolver.query(
                    phoneContentUri,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = $_id",
                    null,
                    null
                )

                var mobile: String = "NONE"
                if (mobileCursor.count > 0) {
                    mobileCursor.moveToFirst()
                    mobile =
                            mobileCursor.getString(mobileCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    mobileCursor.close()
                }


                contacts.add(ContactEntity(Uri.parse(""), name, mobile))
            } while (cursor.moveToNext())

            cursor.close()
        }

        callback.contactFetch(contacts)
    }
}