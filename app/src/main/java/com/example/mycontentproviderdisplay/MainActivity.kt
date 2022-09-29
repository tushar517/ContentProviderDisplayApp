package com.example.mycontentproviderdisplay

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var adapter:StudentAdapter
    var studentList= ArrayList<StudentModel>()
    lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        val btn:Button = findViewById(R.id.refreshBtn)
        val nextBtn:Button = findViewById(R.id.nextActivity)
        adapter = StudentAdapter(studentList)
        val layout = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layout
        generateList()
        btn.setOnClickListener {
            generateList()
            adapter.notifyDataSetChanged()
        }
        nextBtn.setOnClickListener {
            startActivity(Intent(this,ContactActivity::class.java))
        }
    }

    @SuppressLint("Range")
    fun generateList(){
        val AUTHORITY:String = "com.example.broadcastreceiverdemo.MyContentProvider"
        val URI = Uri.parse("content://$AUTHORITY")
        studentList.removeAll(studentList)
        val cursor = contentResolver.query(URI,null,null,null,"_id")
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do{
                    studentList.add(
                        StudentModel(
                            cursor.getString(cursor.getColumnIndex("_id")).toString(),
                            cursor.getString(cursor.getColumnIndex("studentName")).toString(),
                            cursor.getString(cursor.getColumnIndex("studentAge")).toString()
                        )
                    )
                } while (cursor.moveToNext())
                adapter.notifyDataSetChanged()
            }
        }else{
            Log.i("MyLog","null")
        }
    }
}