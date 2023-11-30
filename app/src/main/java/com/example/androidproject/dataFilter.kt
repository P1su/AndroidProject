package com.example.androidproject

import android.widget.Toast
import com.example.androidproject.dataclass.Item

class dataFilter() {
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

         /*   if(it[i].price in startPrice..endPrice){
                filteredList += it[i]
            }*/
        }

        return filteredList
    }
    fun sellingFilter(it : ArrayList<Item>):ArrayList<Item>{
        filteredList.clear()
        for(i in 0..<it.count()){
            if(it[i].userId == "go" && it[i].selled == false){
                filteredList +=it[i]
            }
        }

        return filteredList
    }
    fun selledFilter(it:ArrayList<Item>):ArrayList<Item>{
        filteredList.clear()
        for(i in 0..<it.count()){
            if(it[i].selled == true)//아이디가 일치하고(임시로"go"라고 저장) 판매중이면(not selled){
                filteredList += it[i]
        }

        return filteredList
    }
    fun likeFilter(it: ArrayList<Item>):ArrayList<Item>{
        filteredList.clear()
        for(i in 0..<it.count()){
            if(it[i].like){
                filteredList +=it[i]
            }
        }
        return filteredList
    }

}