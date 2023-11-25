package com.example.androidproject.repository

import android.widget.Toast
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.androidproject.Item
import com.example.androidproject.User
import com.example.androidproject.mainFragment
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.childEvents
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.database.snapshots
import com.google.firebase.database.values

class RecDataRepository {
    val database = Firebase.database
    val userRef = database.getReference("Item")

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

                        userList.postValue(listdata)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun setLike(newValue: Boolean, title:String){

        val map = mutableMapOf<String, Boolean>("like" to newValue)

        userRef.addListenerForSingleValueEvent(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                for(userSnapshot in snapshot.children){

                    val getData = userSnapshot.getValue(Item::class.java)

                    if( getData?.title== title){

                        userRef.child(userSnapshot.key!!).updateChildren(map as Map<String, Any>)
                        getData?.like = newValue
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }






}