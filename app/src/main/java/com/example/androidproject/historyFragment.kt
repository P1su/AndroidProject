package com.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject.databinding.FragmentHistoryBinding


class historyFragment : Fragment() {
    val users = arrayOf(
        User("user1", "last message", "10월 31일"),
        User("user2", "last message", "10월 31일"),
        User("user3", "last message", "10월 30일"),
        User("user4", "last message", "10월 29일"),
        User("user5", "last message", "10월 28일"),
        User("user6", "last message", "10월 26일"),
        User("user7", "last message", "10월 11일"),
        User("user8", "last message", "10월 5일"),
        User("user9", "last message", "9월 21일"),
        User("user10", "last message", "9월 1일")
    )
    var binding: FragmentHistoryBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recUsers?.layoutManager = LinearLayoutManager(context)
        binding?.recUsers?.adapter = UsersAdapter(users)
    }


}