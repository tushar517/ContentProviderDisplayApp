package com.example.mycontentproviderdisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(val studentList:List<StudentModel>):RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(view: View):RecyclerView.ViewHolder(view){
        val studentId:TextView = view.findViewById(R.id.studentID)
        val studentName:TextView = view.findViewById(R.id.studentName)
        val studentAge:TextView = view.findViewById(R.id.studentAge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item, parent, false))
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val item = studentList[position]
        holder.studentId.text = item.id
        holder.studentName.text = item.studentName
        holder.studentAge.text = item.studentAge

    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}