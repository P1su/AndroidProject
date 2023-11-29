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
    val items = ArrayList<Item>()

    init{
        repository.observeUser(_userList)
    }

    fun setLike(newLike : Boolean, title : String){
        repository.setLike(newLike, title)
    }

    fun setData(newList : ArrayList<Item>){
        if(newList.isEmpty()){
            resetData()
        }else {
            _userList.value = newList
        }
    }

    fun resetData(){//필터링된 _userlist를 다시 초기화 시켜준다
        repository.observeUser(_userList)
    }

}