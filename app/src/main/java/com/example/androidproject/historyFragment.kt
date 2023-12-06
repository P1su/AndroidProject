package com.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject.databinding.FragmentHistoryBinding
import com.example.androidproject.viewmodel.HistoryViewModel



class historyFragment : Fragment() {
    val viewModel : HistoryViewModel by viewModels()
    var binding : FragmentHistoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = viewModel.user.value
        user?.let{
            binding?.recUsers?.layoutManager = LinearLayoutManager(context)
            binding?.recUsers?.adapter = HistoryAdapter(user)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}


