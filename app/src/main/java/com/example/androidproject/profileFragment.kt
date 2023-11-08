package com.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.databinding.FragmentMainBinding
import com.example.androidproject.databinding.FragmentProfileBinding

class profileFragment : Fragment() {
    val items = arrayListOf(//리스트의 길이 변경 가능
        Item("Title", "I'm selling", "Ilsan", "P1su",false, false,"none"),
        Item("Coffee", "Selled", "Ilsan", "P1su",true, false,"none"),
        Item("Guitar", "Good", "Seoul", "Joe",false, true,"none"),
        Item("Computer", "Cheap", "Busan", "Kim",false, false,"전자기기"),
        Item("Bike", "New", "Incheon", "Park",false, false,"none"),
        Item("Book", "Old", "Suwon", "Lee",false,false,"none"),
        Item("Mask", "Color is white", "Sejong", "Song",false, false,"none")
    )

    var binding : FragmentProfileBinding ?= null
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

        binding?.btnSelling?.setOnClickListener{
            val filteredList =ArrayList<Item>()
            for(i in items){
                if(i.id == "go" && i.selled == false){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(context,"판매중인 상품이 없습니다.",Toast.LENGTH_SHORT).show()
            }else binding?.recMyitem?.adapter = itemAdapter(filteredList)
        }

        binding?.btnSelled?.setOnClickListener{
            val filteredList =ArrayList<Item>()
            for(i in items){
                if(i.selled == true){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(context,"판매 완료된 없습니다.",Toast.LENGTH_SHORT).show()
            }else binding?.recMyitem?.adapter = itemAdapter(filteredList)
        }

        binding?.btnLikelist?.setOnClickListener{
            val filteredList =ArrayList<Item>()
            for(i in items){
                if(i.like == true){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(context,"찜한 상품이 없습니다.",Toast.LENGTH_SHORT).show()
            }else binding?.recMyitem?.adapter = itemAdapter(filteredList)
        }

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