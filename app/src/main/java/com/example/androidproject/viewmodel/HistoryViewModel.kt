package com.example.androidproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidproject.dataclass.History
import com.example.androidproject.dataclass.Item
import com.example.androidproject.repository.HistroyRepository



class HistoryViewModel: ViewModel(){
    private val repository = HistroyRepository()
    private val _user = MutableLiveData<ArrayList<History>>()

    init{
        repository.observeUsers(_user)
    }
    val user : LiveData<ArrayList<History>> get() = _user



    fun registerProduct(product: Item){
        repository.addProductToDatabase(product)
    }




}
