package com.example.firbase_test_crude

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

//UserAdapter Class:
class UserAdapter(
    private val userListmodel: List<UserListModel>,
    private val context: Context, val onUserClick: setOnClickListener
) : BaseAdapter() {

//     Methods in UserAdapter: a. getCount:
    override fun getCount(): Int {
        return userListmodel.size
    }
//Returns the number of items in the adapter, which is the size of the userListmodel list. b. getItem:
    override fun getItem(position: Int): Any {
        return userListmodel[position]
    }

//Returns the item at the specified position in the adapter. getItemId:
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

//    4. Inside getView Method: View Initialization:
    @SuppressLint("ViewHolder", "MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)
        val name = view.findViewById<TextView>(R.id.userName_TextView)
        val vill = view.findViewById<TextView>(R.id.vill_TextView)
        val number = view.findViewById<TextView>(R.id.number_TextView)
        val deleteItem = view.findViewById<ImageView>(R.id.delete_image)
        val updateImg = view.findViewById<ImageView>(R.id.update_image)

//        Setting Text Values:
        name.text = userListmodel[position].name
        vill.text = userListmodel[position].vill
        number.text = userListmodel[position].number.toString()

//        Update Image Click Listener:
        updateImg.setOnClickListener {
            onUserClick.onUpdateListener(userListmodel[position])
        }

//        Delete Image Click Listener:
        deleteItem.setOnClickListener {
            val dilog = AlertDialog.Builder(context)
            dilog.setTitle("Delete")
            dilog.setMessage("are you Exit")
            dilog.setPositiveButton("ok") { dilog, which ->
                onUserClick.onDeleteListener(userListmodel[position])
            }
            dilog.setNegativeButton("canceled") { dilog, which ->
                dilog.dismiss()
            }
            val alert = dilog.create()
            alert.show()
        }
        return view

    }
}

//5. Interface setOnClickListener:
interface setOnClickListener {

    fun onUpdateListener(user: UserListModel)
    fun onDeleteListener(user: UserListModel)

}