package com.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.databinding.FragmentMainBinding
import com.example.androidproject.databinding.FragmentProfileBinding
import com.example.androidproject.viewmodel.ItemViewModel

class profileFragment : Fragment() {

    var binding : FragmentProfileBinding ?= null
    val viewModel: ItemViewModel by activityViewModels()
    val viewList get() = viewModel.userList//getter 사용하기!! LiveData<Arraylist>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recMyitem?.layoutManager=LinearLayoutManager(context)

        binding?.btnSelling?.setOnClickListener{//판매중인 항목 보여줌
            val filteredList =ArrayList<Item>()
            viewModel?.let{
                for(i in it.items){
                    if(i.id == "go" && i.selled == false)//아이디가 일치하고(임시로"go"라고 저장) 판매중이면(not selled){
                        filteredList += i
                }
            }

            if(filteredList.isEmpty()){
                Toast.makeText(context,"판매중인 상품이 없습니다.",Toast.LENGTH_SHORT).show()
            }else {
                viewModel.setData(filteredList)
                binding?.recMyitem?.adapter = itemViewAdapter(viewList)
            }
        }

        binding?.btnSelled?.setOnClickListener{
            val filteredList =ArrayList<Item>()
            viewModel?.let{
                for(i in it.items){
                    if(i.selled == true)//아이디가 일치하고(임시로"go"라고 저장) 판매중이면(not selled){
                        filteredList += i
                }
            }

            if(filteredList.isEmpty()){
                Toast.makeText(context,"판매 완료된 상품이 없습니다.",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.setData(filteredList)
                binding?.recMyitem?.adapter = itemViewAdapter(viewList)
            }
        }

        binding?.btnLikelist?.setOnClickListener{
            val filteredList =ArrayList<Item>()
            viewModel?.let{
                for(i in it.items){
                    if(i.like == true)//아이디가 일치하고(임시로"go"라고 저장) 판매중이면(not selled){
                        filteredList += i
                }
            }
            if(filteredList.isEmpty()) {
                Toast.makeText(context, "찜한 상품이 없습니다.", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.setData(filteredList)
                binding?.recMyitem?.adapter = itemViewAdapter(viewList)
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


/*    companion object {
        fun newInstance(param1: String, param2: String) =
            mainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}