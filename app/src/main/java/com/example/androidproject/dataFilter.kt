package com.example.androidproject

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.androidproject.dataclass.Item
import com.example.androidproject.viewmodel.ItemViewModel

class DataFilter() {
    private val filteredList = ArrayList<Item>()

    fun dialogFilter(it : ArrayList<Item>, typeList : ArrayList<String>) : ArrayList<Item>{
        filteredList.clear()
        for(i in 0..<it.count()){//뷰모델의 아이템들을 대상으로
            for(j in typeList){//선택된 카테고리와 일치하는지 탐색

                if(it[i].type == j){
                    filteredList += it[i]//일치한다면 새로운 리스트에 아이템을 담아줌
                }
            }
        }
        return filteredList
    }

    fun priceFilter(it : ArrayList<Item>, startPrice: Int, endPrice: Int):ArrayList<Item>{
        filteredList.clear()
        for(i in 0..<it.count()){

            if(it[i].price.toInt() in startPrice..endPrice){
                filteredList += it[i]
            }
        }

        return filteredList
    }
    fun sellingFilter(it : ArrayList<Item>, Id : String):ArrayList<Item>{
        filteredList.clear()
        for(i in 0..<it.count()){
            if(it[i].userId == Id && !it[i].selled){
                filteredList +=it[i]
            }
        }

        return filteredList
    }
    fun selledFilter(it:ArrayList<Item>, Id: String):ArrayList<Item>{
        filteredList.clear()
        for(i in 0..<it.count()){
            if(it[i].selled && it[i].userId == Id)//아이디가 일치하고(임시로"go"라고 저장) 판매중이면(not selled){
                filteredList += it[i]
        }

        return filteredList
    }
    fun likeFilter(it: ArrayList<Item>, Id: String):ArrayList<Item>{
        filteredList.clear()
        for(i in 0..<it.count()){
            if(it[i].like && it[i].userId != Id){
                filteredList +=it[i]
            }
        }
        return filteredList
    }

}