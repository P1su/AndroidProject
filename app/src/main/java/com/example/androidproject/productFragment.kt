package com.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.androidproject.databinding.FragmentProductBinding
import com.example.androidproject.databinding.FragmentProfileBinding
import com.example.androidproject.viewmodel.ItemViewModel


class productFragment : Fragment() {
    var binding : FragmentProductBinding?= null
    val viewModel: ItemViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater)
        binding?.txtProdTitle?.text = arguments?.getString("title")
        binding?.txtProdContent?.text = arguments?.getString("content")
        binding?.txtProdDate?.text = arguments?.getString("date")

        return binding?.root
    }






}