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

        db.update(DATABASE_NAME, contentValues, clause, null)
        db.close()

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
                contactId = cursor.getInt(cursor.getColumnIndex("ID"))
                contactName = cursor.getString(cursor.getColumnIndex("NAME"))
                contactNumber= cursor.getString(cursor.getColumnIndex("NUMBER"))

                var contact = Contact(contactId, contactName, contactNumber)
                contacts.add(contact)

            }while (cursor.moveToNext())
        }
        return contacts
    }




}