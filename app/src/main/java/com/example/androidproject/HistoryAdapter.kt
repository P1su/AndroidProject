package com.example.androidproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.databinding.HistoryListBinding
import com.example.androidproject.dataclass.History


class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.Holder>(){
    var users = ArrayList<History>()

    fun setUserList(data: ArrayList<History>){
        users = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = HistoryListBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(users[position])
    }

    class Holder(private val binding : HistoryListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: History?){
            user?.let{
                binding.txtUserId.text = it.userId
                binding.txtDate.text = it.date
                binding.txtLastMsg.text = it.lastMsg
            }

        }

    }
}