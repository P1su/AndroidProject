package com.example.androidproject.repository

import androidx.lifecycle.MutableLiveData
import com.example.androidproject.dataclass.Item

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

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

                        for(childSnapshot in userSnapshot.children){//2번 탐색,,,,,,

                            val getData = childSnapshot.getValue(Item::class.java)
                            getData?.let {//value는
                                listdata.add(it)
                            }

                            userList.postValue(listdata)
                        }

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
                    for(childSnapshot in userSnapshot.children){
                        val getData = childSnapshot.getValue(Item::class.java)

                        if( getData?.title== title){

                            userSnapshot.key?.let{
                                val userKey = it
                                childSnapshot.key?.let{
                                    userRef.child(userKey).child(it).updateChildren(map as Map<String, Any>)
                                }

                            }

                            getData.like = newValue
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
    fun delData(title:String) {

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (userSnapshot in snapshot.children) {
                    for (childSnapshot in userSnapshot.children) {
                        val getData = childSnapshot.getValue(Item::class.java)

                        if (getData?.title == title) {

                            userSnapshot.key?.let {
                                val userKey = it
                                childSnapshot.key?.let {
                                    userRef.child(userKey).child(it).removeValue()
                                }

                            }

                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }


}