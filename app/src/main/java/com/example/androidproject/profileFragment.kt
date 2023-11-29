package com.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject.databinding.FragmentProfileBinding
import com.example.androidproject.dataclass.Item
import com.example.androidproject.viewmodel.ItemViewModel

class profileFragment : Fragment() {

    private val filter = dataFilter()
    private var filteredList = ArrayList<Item>()
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

            viewList.value?.let{
                filteredList = filter.sellingFilter(it)
            }

            if(filteredList.isEmpty()){
                Toast.makeText(context,"판매중인 상품이 없습니다.",Toast.LENGTH_SHORT).show()
            }else {
                viewModel.setData(filteredList)
                binding?.recMyitem?.adapter = itemViewAdapter(viewList)
            }
        }

        binding?.btnSelled?.setOnClickListener{
            viewList.value?.let {
                filteredList = filter.selledFilter(it)
            }

            if(filteredList.isEmpty()){
                Toast.makeText(context,"판매 완료된 상품이 없습니다.",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.setData(filteredList)
                binding?.recMyitem?.adapter = itemViewAdapter(viewList)
            }
        }

        binding?.btnLikelist?.setOnClickListener{

            viewModel.userList.value?.let{
                filteredList = filter.likeFilter(it)

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