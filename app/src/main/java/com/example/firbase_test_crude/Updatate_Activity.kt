package com.example.firbase_test_crude

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Updatate_Activity : AppCompatActivity() {
    lateinit var updateName: EditText
    lateinit var updateVill: EditText
    lateinit var updateNumber: EditText
    lateinit var updateBtn: Button

    val db = FirebaseFirestore.getInstance()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updatate)

        updateName = findViewById(R.id.updateName_EditText)
        updateVill = findViewById(R.id.updateVill_EditText)
        updateNumber = findViewById(R.id.updateNumber_EditText)
        updateBtn = findViewById(R.id.updateData_Button)

        val id = intent.extras?.getString("key_id")
        val name = intent.extras?.getString("key_name")
        val vill = intent.extras?.getString("key_vill")
        val number = intent.extras?.getString("key_number")

        updateName.setText(name)
        updateVill.setText(vill)
        updateNumber.setText(number)

        updateBtn.setOnClickListener {
            val sname = updateName.text.toString()
            val snumber = updateNumber.text.toString()
            val svill = updateVill.text.toString()
            val map = mapOf(
                "name" to sname,
                "vill" to svill,
                "number" to snumber
            )
            db.collection("Users").document("$id").update(map)
                .addOnSuccessListener {
                    Toast.makeText(this, "User Update Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))

                }
                .addOnFailureListener {
                    Toast.makeText(this, "User Update failed", Toast.LENGTH_SHORT).show()
                }
        }
    }
}