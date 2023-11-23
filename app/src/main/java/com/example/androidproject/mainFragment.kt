package com.example.androidproject

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.databinding.FragmentMainBinding
import com.example.androidproject.viewmodel.ItemViewModel
import com.google.android.material.slider.RangeSlider

class mainFragment : Fragment() {

    private var searchViewTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {//검색 버튼 입력시 호출
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {//텍스트 입력시 호출
            filterList(newText)//텍스트가 입력되면 filterlist 함수로 텍스트를 넘김
            return true
        }

    }

    val viewModel: ItemViewModel by activityViewModels()
    var binding: FragmentMainBinding? = null
    val viewList get() = viewModel.userList//getter 사용하기!! LiveData<Arraylist>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        return binding?.root
    }
    val itemViewAdapter get()= itemViewAdapter(viewList)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewList.observe(viewLifecycleOwner) {
            viewList.let {
                binding?.recItem?.layoutManager = LinearLayoutManager(context)
                binding?.recItem?.adapter = itemViewAdapter
            }
        }
        binding?.searchTitle?.setOnQueryTextListener(searchViewTextListener)
        binding?.btnCategory?.setOnClickListener {
               showDialog()
        }
        binding?.btnPrice?.setOnClickListener {
                  showBar()
        }
        binding?.btnTomap?.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_mapFragment)
        }
        binding?.btnLoaction?.setOnClickListener {
            //showLocSet()
        }

    }

    private fun filterList(query: String?) {//입력된 텍스트를 받아옴
        val filteredList = MutableLiveData<ArrayList<Item>>()//새 리사이클러뷰를 위한 리스트 생성

        query?.let {//텍스트가 널이 아니면
            viewList.value?.let {
                for(i in it){
                    if(i.title.lowercase().contains(query)){
                        filteredList.value?.add(i)
                    }
                }
            }
            itemViewAdapter(filteredList)

        }
    }
    private fun showDialog() {
        var selectedCategory = arrayListOf<String>()//선택된 항목 담음
        var num = 1
        var builder = AlertDialog.Builder(context)?.let { it ->//다이얼로그 구현
            it.setTitle("카테고리")//다이얼로그의 이름
            //멀티 선택 이벤트
            it.setMultiChoiceItems( // 아이템을 여러개 선택
                R.array.categories, null//string.xml파일에 데이터를 저장
            ) { dialog, which, isChecked -> // DialogInterface.OnMultiChoiceClickListener object의 override onClick함수 << 람다형식
                val category = resources.getStringArray(R.array.categories)//category데이터를 임시로 담아둔다

                if (isChecked) {//체크되면 추가하고 아니면 삭제
                    selectedCategory += category[which]
                } else selectedCategory -= category[which]

            }

            it.setPositiveButton("확인") { dialog, which ->
                val filteredList = ArrayList<Item>()
                //val filteredList = MutableLiveData<ArrayList<Item>>()
                viewModel.userList.value?.let{
                    for(i in 0..<it.count()){//뷰모델의 아이템들을 대상으로
                        for(j in selectedCategory){//선택된 카테고리와 일치하는지 탐색
                            if(it[i].category == j){
                                filteredList += it[i]
                               // filteredList.value?.add(i) //일치한다면 새로운 리스트에 아이템을 담아줌
                            }
                        }
                    }
                }
                if (filteredList.isEmpty()) {//해당하는 제품이 아뭓도 없다면
                    Toast.makeText(context, "해당하는 상품이 없습니다.", Toast.LENGTH_SHORT).show()
                    viewModel.setData(viewModel.items)//토스트 메세지 띄우면서 처음 상태로 초기화
                } else viewModel.setData(filteredList)//viewModel.setData(filteredList)//해당하는 제품들만 노출

            }

            it.setNegativeButton("닫기") { dialog, which -> // Dialoginterface.OnclieckListner...dialog는 DialogInterface>, which는 int이다
                dialog.cancel()//닫으면서 초기화
                viewModel.setData(viewModel.items)
            }

            it.show()//다일얼로그를 보여줌
        }

    }

    private fun showBar() {

        var startPrice : Int = 0
        var endPrice : Int = 0

        var RangeView = layoutInflater.inflate(R.layout.price_bar, null)//View타입,,price slide range bar를 가져온다.
        var rangeSlider : RangeSlider = RangeView.findViewById(R.id.rangeSlider)//레인지바 설정

        rangeSlider.addOnSliderTouchListener(object :RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {//터치했을때..

            }

            override fun onStopTrackingTouch(slider: RangeSlider) {//손을 뗐을때...

                startPrice = slider.values[0].toInt()//슬라이더의 처음 값과 마지막 값을 담아준다.
                endPrice = slider.values[1].toInt()
            }

        })
        var builder = AlertDialog.Builder(context)?.let {
            it.setTitle("가격 범위를 설정하세요.")//다이얼로그 제목
            it.setView(RangeView)//슬라이드바를 보여준다

            it.setPositiveButton("확인"){dialog , which -> //다이얼로그의 내장 함수. 슬라이드 범위 설정 후 확인을 누를 시
                val filteredList = ArrayList<Item>()
                viewModel?.let {
                    for(i in it.items){
                        if(i.price >= startPrice && i.price <= endPrice){
                            filteredList += i
                        }
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(context, "희망하시는 가격대의 상품이 없습니다.", Toast.LENGTH_SHORT).show()
                    viewModel.setData(viewModel.items)
                } else {
                    viewList.observe(viewLifecycleOwner){
                        viewModel.setData(filteredList)
                    }
                }


            }
            it.setNegativeButton("닫기"){dialog , which ->
                viewModel.setData(viewModel.items)
            }

            it.show()
        }

    }

  /*  private fun showLocSet() {
        var builder = AlertDialog.Builder(context)
        var seekView = layoutInflater.inflate(R.layout.location_bar, null)//View타입
        var seekBar : SeekBar = seekView.findViewById(R.id.seekBar)


        builder.setTitle("거래 거리를 선택해주세요.")
        builder.setView(seekView)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var text :TextView=seekView.findViewById(R.id.text_progress)
                text.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        builder.show()


    }*/
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}


