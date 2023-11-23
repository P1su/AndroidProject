package com.example.androidproject.viewmodel

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidproject.Item
import com.example.androidproject.itemAdapter
import com.example.androidproject.repository.RecDataRepository

class ItemViewModel:ViewModel(){
    private val _userList = MutableLiveData<ArrayList<Item>>()
    val userList : LiveData<ArrayList<Item>> get() = _userList //외부에서 간섭하면 내부에서 _userList를 변경

    var items =ArrayList<Item>()
    private val repository = RecDataRepository()
    init{
      /*  items = arrayListOf(//리스트의 길이 변경 가능
            Item("Title", "I'm selling", "Ilsan", "P1su",false, false,"가전제품",50000),
            Item("Coffee", "Selled", "Ilsan", "P1su",true, false,"none",10000),
            Item("Guitar", "Good", "Seoul", "Joe",false, true,"악기",200000),
            Item("Computer", "Cheap", "Busan", "Kim",false, false,"전자기기",2000000),
            Item("Bike", "New", "Incheon", "Park",false, false,"none",500000),
            Item("TV", "Old", "Suwon", "Lee",false,false,"전자기기",5000),
            Item("Mask", "Color is white", "Sejong", "Song",false, false,"none",1000)
        )
        _userList.value = items*/
        repository.observeUser(_userList)
    }

    fun setLike(newValue : Boolean){
        Log.d("In viewmodel", "$newValue")
        repository.setLike(newValue)

    }
    fun setData(newList : ArrayList<Item>){
        if(newList.isEmpty()){
            repository.observeUser(_userList)
        }else repository.setData(newList)
    }


}