package com.example.androidproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidproject.dataclass.History
import com.example.androidproject.dataclass.RegisterInfo
import com.example.androidproject.repository.HistroyRepository



class HistoryViewModel: ViewModel(){
    private val _user = MutableLiveData<ArrayList<History>>()
    val user : LiveData<ArrayList<History>> get() = _user

    private val repository = HistroyRepository()


    fun fetchUser(): LiveData<ArrayList<History>>{
        repository.getUser().observeForever{
            _user.value = it
        }
        return _user
    }

    fun registerProduct(product: RegisterInfo){
        repository.addProductToDatabase(product)
    }




}
