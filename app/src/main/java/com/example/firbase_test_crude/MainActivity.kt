package com.example.firbase_test_crude
// Imports:
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore


//Class Declaration:
class MainActivity : AppCompatActivity(), setOnClickListener {

//     Properties:
    private lateinit var saveImge: ImageView
    private lateinit var studentList: ListView
    private lateinit var addUserBtn: FloatingActionButton
    private lateinit var userAdapter: UserAdapter

//    Firebase Firestore Initialization:
    val db = FirebaseFirestore.getInstance()

//    onCreate Method:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//    UI Initialization:
        saveImge = findViewById(R.id.saveImage_ImageView)
        studentList = findViewById(R.id.students_ListView)
        addUserBtn = findViewById(R.id.add_Button)

//    User Adapter Initialization:
        val userArray = ArrayList<UserListModel>()
        userAdapter = UserAdapter(userArray, this, this)
        studentList.adapter = userAdapter

//    Showing User Data:
        showUserData()

//    Add User Button Click Listener:
        addUserBtn.setOnClickListener {
            startActivity(Intent(this, Add_UserActivity::class.java))
        }
    }

//     showUserData Method:
    private fun showUserData() {
        db.collection("Users").get()
            .addOnSuccessListener {
                val data = it.toObjects(UserListModel::class.java)
                userAdapter = UserAdapter(data, this, this)
                studentList.adapter = userAdapter
                Toast.makeText(this, "Add data success", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }
            .addOnFailureListener {
                Toast.makeText(this, "Add data Fail", Toast.LENGTH_SHORT).show()
                return@addOnFailureListener
            }
    }


//    onUpdateListener and onDeleteListener:
    override fun onUpdateListener(user: UserListModel) {
        val intent = Intent(this@MainActivity, Updatate_Activity::class.java)
        intent.putExtra("key_id", user.id.toString())
        intent.putExtra("key_name", user.name.toString())
        intent.putExtra("key_vill", user.vill.toString())
        intent.putExtra("key_number", user.number.toString())
        startActivity(intent)
    }

    override fun onDeleteListener(user: UserListModel) {
        deleteUser(user.id.toString())
    }

//    deleteUser Method:
    fun deleteUser(id: String) {
        db.collection("Users").document(id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "users deleted success", Toast.LENGTH_SHORT).show()
                showUserData()

            }

            .addOnFailureListener {
                Toast.makeText(this, "users deleted failed", Toast.LENGTH_SHORT).show()
            }
    }


}