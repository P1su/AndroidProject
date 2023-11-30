package com.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject.databinding.FragmentHistoryBinding
import com.example.androidproject.viewmodel.HistoryViewModel
import com.google.firebase.database.DatabaseReference



class historyFragment : Fragment() {

    var binding : FragmentHistoryBinding? = null

    private lateinit var adapter: HistoryAdapter

    val viewModel : HistoryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUser()
        binding?.recUsers?.layoutManager = LinearLayoutManager(context)
        binding?.recUsers?.adapter = HistoryAdapter()

    }

    fun observeUser(){
        viewModel.fetchUser().observe(viewLifecycleOwner){
            adapter.setUserList(it)
        }
    }


}


