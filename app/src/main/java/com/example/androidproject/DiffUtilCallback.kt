package com.example.androidproject

import androidx.recyclerview.widget.DiffUtil
import com.example.androidproject.dataclass.Item

//notifydatasetchanged는 모든 데이터를 하나씩 변경. 기존 바인딩된 데이터 지우고
//처음부터 다시 랜더링 ,,,diffutil을 통하여 변경된 부분만 업데이트 시킨다.
class DiffUtilCallback(private val oldData : ArrayList<Item>?, private val newData : ArrayList<Item>?): DiffUtil.Callback() {
    override fun getOldListSize(): Int {//이전 목록 갯수 반환
        return oldData?.size?:0
    }

    override fun getNewListSize(): Int {//현재 목록 갯수 반환
        return newData?.size?:0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {//두 객체가 같은지
        lateinit var oldItem :Item
        lateinit var newItem :Item
        oldData?.let{
            oldItem = it[oldItemPosition]
        }
        newData?.let{
            newItem = it[newItemPosition]
        }

        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {//두 항목의 데이터가 같은지,,,areItemsTheSame이 true일때만! 객체가 다르면 값 비교는 의미 x
        lateinit var oldItem :Item
        lateinit var newItem :Item
        oldData?.let{
            oldItem = it[oldItemPosition]
        }
        newData?.let{
            newItem = it[newItemPosition]
        }
        return oldItem == newItem

    }


}