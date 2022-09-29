package com.example.mycontentproviderdisplay

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactActivity : AppCompatActivity() {
    lateinit var adapter: ContactAdapter
    val REQUEST_READ_CONTACTS = 79
    var contactList = ArrayList<ContactModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        val contactRecyclerView:RecyclerView = findViewById(R.id.contactRecyclerView)
        val btn: Button = findViewById(R.id.refreshBtn)

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            contactList = getContactList()
        }else{
            requestPermission()
        }
        adapter = ContactAdapter(contactList)
        contactRecyclerView.adapter = adapter
        contactRecyclerView.layoutManager = LinearLayoutManager(this)
        btn.setOnClickListener {
            contactList.removeAll(contactList)
            contactList.addAll(getContactList())
            adapter.notifyDataSetChanged()
        }
    }

    private fun requestPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_CONTACTS)){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS),
                REQUEST_READ_CONTACTS);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS),
                REQUEST_READ_CONTACTS);
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_READ_CONTACTS->{
                if (grantResults.size > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    contactList = getContactList()
                }else{
                    requestPermission()
                }
            }
            else->{
                requestPermission()
            }
        }
    }
    @SuppressLint("Range", "NewApi")
    @JvmName("getContactList1")
    private fun getContactList():ArrayList<ContactModel> {
        var list = ArrayList<ContactModel>()
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null)
        if(cursor!=null && cursor.count>0){
            while (cursor != null && cursor.moveToNext()) {
                val id = cursor!!.getString(
                    cursor!!.getColumnIndex(ContactsContract.Contacts._ID));
                val name = cursor!!.getString(cursor!!.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));

                if (cursor!!.getInt(cursor!!.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val pCur = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    arrayOf(id), null);
                    while (pCur!!.moveToNext()) {
                        val phoneNo = pCur.getString(pCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                    list.add(ContactModel(name,phoneNo.toString()))
                    }
                    pCur.close();
                }
            }
        }
        if (cursor != null) {
            cursor!!.close();
        }
        return list
    }
}