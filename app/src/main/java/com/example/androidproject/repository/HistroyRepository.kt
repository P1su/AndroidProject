package com.example.androidproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidproject.dataclass.History
import com.example.androidproject.dataclass.Item
import com.example.androidproject.dataclass.RegisterInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HistroyRepository {
    val database = Firebase.database

    fun getUser(): LiveData<ArrayList<History>> {
        val users = MutableLiveData<ArrayList<History>>()
        val userRef = database.getReference("User")

        userRef.addValueEventListener(object: ValueEventListener {
            val userlist = ArrayList<History>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for( snap in snapshot.children){
                        val user = snap.getValue<History>()
                        user?.let{
                            userlist.add(it)
                        }
                        users.value = userlist
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        return users
    }

    fun addProductToDatabase(product: Item){
        val productRef = database.getReference("Item")
        productRef.push().setValue(product)

       /*
        val user = History()
        database.getReference("User").push().setValue(user)
        database.getReference("User").push().setValue(user)
        database.getReference("User").push().setValue(user)
        database.getReference("User").push().setValue(user)
        database.getReference("User").push().setValue(user)

        */


    }


}
