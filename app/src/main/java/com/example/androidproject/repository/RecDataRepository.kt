package com.example.androidproject.repository

import androidx.lifecycle.MutableLiveData
import com.example.androidproject.Item
import com.example.androidproject.User
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class RecDataRepository {
    val database = Firebase.database
    val userRef = database.getReference("UserData")

    fun observeUser(userList : MutableLiveData<ArrayList<Item>>){
        userRef.addValueEventListener(object:ValueEventListener{ //함수 2개의 객체,,,,를 상속받아서 event에 담음,,일종의 인터페이스

            val listdata: ArrayList<Item> = ArrayList()

            override fun onDataChange(snapshot: DataSnapshot) {//data를 읽어오고 바뀔 때
                if(snapshot.exists()){
                    listdata.clear()
                    for(userSnapshot in snapshot.children){
                        val getData = userSnapshot.getValue(Item::class.java)
                        getData?.let {//value는
                            listdata.add(it)
                        }
                        userList.value = listdata

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun setLike(newValue: Boolean ){

        userRef.child("User_01").child("good").setValue(newValue)

    }

    fun setData(newValue: ArrayList<Item>){
        userRef.setValue(newValue)
    }


}