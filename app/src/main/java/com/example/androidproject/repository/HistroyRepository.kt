package com.example.androidproject.repository

import androidx.lifecycle.MutableLiveData
import com.example.androidproject.dataclass.History
import com.example.androidproject.dataclass.Item
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HistroyRepository {
    val database = Firebase.database

    fun observeUsers(users : MutableLiveData<ArrayList<History>>) {
        val userRef = database.getReference("User")

        userRef.addValueEventListener(object: ValueEventListener {
            val usersData = ArrayList<History>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for( snap in snapshot.children){
                        val user = snap.getValue<History>()
                        user?.let{
                            usersData.add(it)
                        }
                    }
                    users.postValue(usersData)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun addProductToDatabase(product: Item){
        val productRef = database.getReference("Item")
        productRef.child(product.userId).push().setValue(product)

    }



}
