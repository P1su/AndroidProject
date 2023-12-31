package com.example.androidproject

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject.databinding.FragmentMainBinding
import com.example.androidproject.dataclass.Item
import com.example.androidproject.viewmodel.ItemViewModel
import com.google.android.material.slider.RangeSlider
class mainFragment : Fragment(), OnRecyclerViewClickListener {

    private var searchViewTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {//검색 버튼 입력시 호출
        //    filterList(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {//텍스트 입력시 호출
            filterList(newText)//텍스트가 입력되면 filterlist 함수로 텍스트를 넘김
            return true
        }

    }

    private val viewModel: ItemViewModel by activityViewModels()//뷰모델
    var binding: FragmentMainBinding? = null//바인딩
    private val viewList get() = viewModel.userList//getter로 LiveData<Arraylist> 호출
    private val filter = DataFilter()//필터링 처리하는 함수 담은 클래스
    private var filteredList = ArrayList<Item>()//필터링된 아이템 담음
    private val tempList get()=viewList.value//필터 해제에 필요한 초기화 데이터

    private lateinit var adapter:itemViewAdapter//어댑터



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
    //private val itemViewAdapter get()= itemViewAdapter2(viewList, this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewList.observe(viewLifecycleOwner) {//뷰모델 관찰
            viewList.value?.let {
                adapter = itemViewAdapter(it, this)//초기 어댑터는 뷰리스트 전체를 담아준다.
            }

            binding?.recItem?.layoutManager = LinearLayoutManager(context)
            binding?.recItem?.adapter = adapter

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

    override fun onLikeClick(value: Boolean, title:String) {//좋아요 버튼 클릭시
        if(value) Toast.makeText(context, "찜했습니다.", Toast.LENGTH_SHORT).show() else Toast.makeText(context, "찜 취소", Toast.LENGTH_SHORT).show()

        viewModel.setLike(value, title) //뷰모델로 변경된 좋아요 값을 보내줌

    }

    override fun onViewClick(pos : Int) {//해당 아이템 클릭시
        val args = Bundle()//넘겨줄 번들 생성

        viewList.value?.let{
            args.putString("title", it[pos].title)
            args.putString("content", it[pos].content)
            args.putString("date", it[pos].date)
        }

        findNavController().navigate(R.id.action_mainFragment_to_productFragment,args)//화면을 이동하면서 argument(번들)을 같이 넘겨줌

    }
    private fun filterList(query: String?) {//입력된 텍스트를 받아옴

        query?.let {//텍스트가 널이 아니면

            viewList.value?.let {
                filteredList = filter.queryFilter(it, query)

            }
            adapter.changeData2(filteredList)
        }


    }
    private fun showDialog() {
        var selectedCategory = arrayListOf<String>()//선택된 항목 담음

        var builder = AlertDialog.Builder(context)?.let { it ->//다이얼로그 구현
            it.setTitle("카테고리")//다이얼로그의 이름

            it.setMultiChoiceItems( // 멀티선택 이벤트 ,,, 아이템을 여러개 선택
                R.array.categories, null//string.xml파일에 데이터를 저장
            ) { dialog, which, isChecked -> // DialogInterface.OnMultiChoiceClickListener object의 override onClick함수 << 람다형식

                val category = resources.getStringArray(R.array.categories)//category데이터를 임시로 담아둔다

                if (isChecked) {//체크되면 추가하고 아니면 삭제
                    selectedCategory += category[which]
                } else selectedCategory -= category[which]

            }

            it.setPositiveButton("확인") { dialog, which ->

                viewList.value?.let {
                    filteredList = filter.dialogFilter(it, selectedCategory)
                        //   itemViewAdapter(filteredList,this)
                }

                if (filteredList.isEmpty()) {//해당하는 제품이 아뭓도 없다면
                    Toast.makeText(context, "해당하는 상품이 없습니다.", Toast.LENGTH_SHORT).show()//토스트 메세지 띄운다.

                } else {
                    adapter.changeData(filteredList)
                }//해당하는 제품들만 노출

            }

            it.setNegativeButton("닫기") { dialog, which -> // Dialoginterface.OnclieckListner...dialog는 DialogInterface>, which는 int이다
                dialog.cancel()//닫으면서 초기화

                adapter.changeData(tempList)
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

                viewList.value?.let {
                    filteredList = filter.priceFilter(it, startPrice, endPrice)

                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(context, "희망하시는 가격대의 상품이 없습니다.", Toast.LENGTH_SHORT).show()

                } else adapter.changeData(filteredList)


            }

            it.setNegativeButton("닫기"){dialog , which ->
                dialog.cancel()
                adapter.changeData(tempList)
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