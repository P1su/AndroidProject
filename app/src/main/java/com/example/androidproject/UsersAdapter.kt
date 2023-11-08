package com.example.androidproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.databinding.UsersListBinding

class UsersAdapter(val users: Array<User>)
    : RecyclerView.Adapter<UsersAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = UsersListBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(users[position])
    }

    class Holder(private val binding : UsersListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.txtName.text = user.name
            binding.txtDate.text = user.date
            binding.txtMsg.text = user.msg

        }

    }
}