package com.example.androidproject

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.databinding.ItemListBinding
import com.example.androidproject.viewmodel.ItemViewModel

class itemViewAdapter(private var items: LiveData<ArrayList<Item>>): RecyclerView.Adapter<itemViewAdapter.Holder>() {//배열 정의된 item을 생성자로 받음
    //val viewModel = ItemViewModel()

    fun setFilteredList(items : LiveData<ArrayList<Item>>){//items를 새롭게 정의
        this.items = items
        notifyDataSetChanged()//데이터가 변경됨을 알림.
    }

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



        // Log.d("In bind", "${items[0].like}")

    }

    class Holder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        //뷰홀더
        fun bind(items: Item) {
            val viewModel = ItemViewModel()
            val btn = binding.btnLike as Button

            binding.txtTitle.text = items.title
            binding.txtBody.text = items.body
            binding.txtLocation.text = items.location
            binding.txtId.text = items.id

            binding.btnLike.setOnClickListener {
                Log.d("${items.title}", "${items.like}")
                items.like != items.like
                viewModel.setLike(items.like)
                Log.d("send like", "${items.like}")
            }


            //  val btn = binding.btnLike as Button

        }
    }
}