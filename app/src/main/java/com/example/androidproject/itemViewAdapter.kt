package com.example.androidproject

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.databinding.ItemListBinding
import com.example.androidproject.dataclass.Item
import com.example.androidproject.viewmodel.ItemViewModel

class itemViewAdapter(private var items: LiveData<ArrayList<Item>>): RecyclerView.Adapter<itemViewAdapter.Holder>() {//배열 정의된 item을 생성자로 받음

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {//viewholder가 생성
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context))//,parent, false

        return Holder(binding)//Holder의 생성자로 binding(view) 객체 넘겨줌...디자인 레이아웃!!
    }

    override fun getItemCount(): Int {
        return items.value?.size ?: 0
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {//뷰홀더에 데이터 바인딩....

        items.value?.get(position)?.let {
            holder.bind(it)
        }


    }

    class Holder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        //뷰홀더

        fun bind(items: Item) {

            val viewModel = ItemViewModel()
            val btn = binding.btnLike as Button

            binding.txtTitle.text = items.title
            binding.txtBody.text = items.content
            binding.txtLocation.text = items.location
            binding.txtId.text = items.userId

            binding.btnLike.setOnClickListener {

                if(items.like == false)items.like = true
                else items.like = false

                viewModel.setLike(items.like, items.title)

            }



        }
    }
}