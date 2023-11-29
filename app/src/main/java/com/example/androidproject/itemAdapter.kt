package com.example.androidproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.databinding.ItemListBinding
import com.example.androidproject.dataclass.Item

class itemAdapter(private var items: ArrayList<Item>):RecyclerView.Adapter<itemAdapter.Holder>() {//배열 정의된 item을 생성자로 받음

    fun setFilteredList(items : ArrayList<Item>){//items를 새롭게 정의
        this.items = items
        notifyDataSetChanged()//데이터가 변경됨을 알림.
    }

    fun setLike(items : ArrayList<Item>){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {//viewholder가 생성
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context))//,parent, false

        return Holder(binding)//Holder의 생성자로 binding(view) 객체 넘겨줌...디자인 레이아웃!!
    }
    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {//뷰홀더에 데이터 바인딩....
        holder.bind(items[position])//
    }

    class Holder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root){//뷰홀더
        fun bind(items: Item){
            binding.txtTitle.text = items.title
            binding.txtBody.text = items.body
            binding.txtLocation.text = items.location
            binding.txtId.text = items.id

            binding.btnLike.setOnClickListener{

            }


        }


    }

}