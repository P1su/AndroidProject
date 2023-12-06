package com.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject.databinding.FragmentProfileBinding
import com.example.androidproject.dataclass.Item
import com.example.androidproject.viewmodel.ItemViewModel

class profileFragment : Fragment(),OnRecyclerViewClickListener {

    private val filter = DataFilter()
    private var filteredList = ArrayList<Item>()

    private val tempList get()=viewList.value//필터 해제에 필요한 초기화 데이터

    var binding : FragmentProfileBinding ?= null
    private val viewModel: ItemViewModel by activityViewModels()
    private val viewList get() = viewModel.userList//getter 사용하기!! LiveData<Arraylist>

    private lateinit var adapter: itemViewAdapter

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
        val Id = binding?.txtMyId?.text.toString()

        viewList.value?.let{

            filteredList = filter.sellingFilter(it, Id)//처음 프로필로 들어가면 판매중인 물건 보여줌
            adapter= itemViewAdapter(filteredList, this)

        }

        viewList.observe(viewLifecycleOwner){
            binding?.recMyitem?.layoutManager = LinearLayoutManager(context)
            binding?.recMyitem?.adapter = adapter
        }


        binding?.btnSelling?.setOnClickListener{//판매중인 항목 보여줌....

            viewList.value?.let{
                filteredList = filter.sellingFilter(it, Id)
            }

            if(filteredList.isEmpty()){
                Toast.makeText(context,"판매중인 상품이 없습니다.",Toast.LENGTH_SHORT).show()
            }else {
                adapter.changeData2(filteredList)

            }
        }

        binding?.btnSelled?.setOnClickListener{

            viewList.value?.let {
                filteredList = filter.selledFilter(it, Id)
            }

            if(filteredList.isEmpty()){
                Toast.makeText(context,"판매 완료된 상품이 없습니다.",Toast.LENGTH_SHORT).show()
            }else{
                adapter.changeData(filteredList)
            }

        }

        binding?.btnLikelist?.setOnClickListener{

            viewList.value?.let{
                filteredList = filter.likeFilter(it, Id)

            }
            if(filteredList.isEmpty()) {
                Toast.makeText(context, "찜한 상품이 없습니다.", Toast.LENGTH_SHORT).show()
            }else{
              adapter.changeData2(filteredList)

            }
        }

    }
    override fun onLikeClick(value: Boolean, title:String) {
        if(value) Toast.makeText(context, "찜했습니다.", Toast.LENGTH_SHORT).show() else Toast.makeText(context, "찜 취소", Toast.LENGTH_SHORT).show()

        viewModel.setLike(value, title)

    }

    override fun onViewClick(pos: Int) {
        val args = Bundle()//넘겨줄 번들 생성

        viewList.value?.let{
            args.putString("title", it[pos].title)
            args.putString("content", it[pos].content)
            args.putString("date", it[pos].date)
        }

        findNavController().navigate(R.id.action_profileFragment_to_productFragment,args)
    }
    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }


}