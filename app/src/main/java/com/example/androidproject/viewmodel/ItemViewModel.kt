package com.example.androidproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidproject.dataclass.Item
import com.example.androidproject.repository.RecDataRepository

class ItemViewModel:ViewModel(){
    private val _userList = MutableLiveData<ArrayList<Item>>()
    val userList : LiveData<ArrayList<Item>> get() = _userList //외부에서 간섭하면 내부에서 _userList를 변경
    private val repository = RecDataRepository()

    init{
        repository.observeUser(_userList)
    }

    fun setLike(newLike : Boolean, title : String){
        repository.setLike(newLike, title)
    }
    fun delData(title: String){
        repository.delData(title)

    }

}