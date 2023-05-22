package com.example.contact.sql

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contact.model.Contact

class DBHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object{
        private const val DATABASE_NAME = "ContactDataBase"
        private const val TABLE_CONTACT = "ContactTable"
        private const val CONTACT_ID = "ID"
        private const val CONTACT_NAME = "NAME"
        private const val CONTACT_NUMBER = "NUMBER"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var query = "CREATE TABLE $TABLE_CONTACT " +
                "($CONTACT_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "$CONTACT_NAME TEXT," +
                "$CONTACT_NUMBER TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addContact(contact: Contact){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONTACT_NAME, contact.name)
        contentValues.put(CONTACT_NUMBER, contact.number)
        db.insert(TABLE_CONTACT, null, contentValues)
        db.close()
    }

    fun deleteContact(index:Int){
        val db = this.writableDatabase
        var clause = "$CONTACT_ID = $index"
        db.delete(TABLE_CONTACT,clause, null)
        db.close()
    }

    fun updateContact(contact: Contact){
        var db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(CONTACT_NAME, contact.name)
        contentValues.put(CONTACT_NUMBER, contact.number)

        var clause = "$CONTACT_ID = ${contact.id}"

        db.update(TABLE_CONTACT, contentValues, clause, null)
        db.close()

    }

    @SuppressLint("Range")
    fun getContact(id : Int):Contact{
        var db = this.readableDatabase

        var query = "SELECT * FROM $TABLE_CONTACT WHERE $CONTACT_ID = $id"

        var cursor : Cursor? = null
        cursor = db.rawQuery(query, null)

        if (cursor!=null)
            cursor.moveToFirst()

        var contactId : Int
        var contactName : String
        var contactNumber : String

        contactId = cursor.getInt(0)
        contactName = cursor.getString(1)
        contactNumber= cursor.getString(2)

        var contact = Contact(contactId, contactName, contactNumber)

        return contact


    }

    @SuppressLint("Range")
    fun getAllContacts() : ArrayList<Contact>{
        var contacts = ArrayList<Contact>()

        var db = this.readableDatabase
        var query = "SELECT * FROM $TABLE_CONTACT"

        var cursor : Cursor? = null

        cursor = db.rawQuery(query, null)

        var contactId : Int
        var contactName : String
        var contactNumber : String

        if (cursor.moveToFirst()){

            do {
                contactId = cursor.getInt(cursor.getColumnIndex(CONTACT_ID))
                contactName = cursor.getString(cursor.getColumnIndex(CONTACT_NAME))
                contactNumber= cursor.getString(cursor.getColumnIndex(CONTACT_NUMBER))

                var contact = Contact(contactId, contactName, contactNumber)
                contacts.add(contact)

            }while (cursor.moveToNext())
        }
        return contacts
    }




}