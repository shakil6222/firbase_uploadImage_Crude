package com.example.firbase_test_crude

//Imports:
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID
// Class Declaration:
class Add_UserActivity : AppCompatActivity() {

//    Properties:
    lateinit var userName: EditText
    lateinit var userNumber: EditText
    lateinit var vill: EditText
    lateinit var storData: Button
    val db = FirebaseFirestore.getInstance()

//    onCreate Method:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        userName = findViewById(R.id.editName_EditText)
        userNumber = findViewById(R.id.editNumber_EditText)
        vill = findViewById(R.id.editVill_EditText)
        storData = findViewById(R.id.storData_Button)

        storData.setOnClickListener {
            storeUserData()
        }
    }


//    store User Data Method:
    private fun storeUserData() {
        val uName = userName.text.toString()
        val uNumber = userNumber.text.toString()
        val uVill = vill.text.toString()

        val id = UUID.randomUUID().toString()

        val map = hashMapOf(
            "id" to id,
            "name" to uName,
            "vill" to uVill,
            "number" to uNumber
        )
        db.collection("Users").document(id).set(map)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "store data success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "store data fail", Toast.LENGTH_SHORT).show()
            }
    }
}