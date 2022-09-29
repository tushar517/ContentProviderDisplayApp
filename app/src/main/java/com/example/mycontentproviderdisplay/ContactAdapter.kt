package com.example.mycontentproviderdisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(val contactList:List<ContactModel>):RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(mView: View):RecyclerView.ViewHolder(mView){
        val txtName:TextView = mView.findViewById(R.id.studentName)
        val txtSNo:TextView = mView.findViewById(R.id.studentID)
        val phoneNo:TextView = mView.findViewById(R.id.studentAge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.student_item,parent,false))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.txtName.text = contactList.get(position).name
        holder.txtSNo.text = (position+1).toString()
        holder.phoneNo.text = contactList[position].phoneNumber
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}