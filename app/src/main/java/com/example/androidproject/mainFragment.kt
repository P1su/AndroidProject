package com.example.androidproject

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.databinding.FragmentMainBinding

class mainFragment : Fragment() {
    val items = arrayListOf(//리스트의 길이 변경 가능
        Item("Title", "I'm selling", "Ilsan", "P1su",false, false,"none"),
        Item("Coffee", "Selled", "Ilsan", "P1su",true, false,"none"),
        Item("Guitar", "Good", "Seoul", "Joe",false, true,"none"),
        Item("Computer", "Cheap", "Busan", "Kim",false, false,"전자기기"),
        Item("Bike", "New", "Incheon", "Park",false, false,"none"),
        Item("Book", "Old", "Suwon", "Lee",false,false,"none"),
        Item("Mask", "Color is white", "Sejong", "Song",false, false,"none")
    )

    private var adapter = itemAdapter(items)

    var searchViewTextListener : SearchView.OnQueryTextListener = object :SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {//검색 버튼 입력시 호출
            return false
        }
        override fun onQueryTextChange(newText: String?): Boolean {//텍스트 입력시 호출
            filterList(newText)//텍스트가 입력되면 filterlist 함수로 텍스트를 넘김
            return true
        }

    }
    private fun filterList(query:String?){//입력된 텍스트를 받아옴
        val filteredList =ArrayList<Item>()//새 리사이클러뷰를 위한 리스트 생성
        query?.let{//텍스트가 널이 아니면

            for(i in items){//반복문 돌면서
                if (i.title.lowercase().contains(it)){ //제목을 소문자로 변경 > 쿼리값 포함하고 있으면
                    filteredList.add(i)//새 리스트에 item 목록을 추가함
                }
            }
            adapter.setFilteredList(filteredList) // 탐색이 끝났으면 어댑터를 새롭게 구성
        }

    }

    var binding : FragmentMainBinding ?= null

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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recItem?.layoutManager=LinearLayoutManager(context)//수직으로 보여줌
        binding?.recItem?.adapter = adapter
        binding?.searchTitle?.setOnQueryTextListener(searchViewTextListener)
        binding?.btnCategory?.setOnClickListener {
            showDialog()
        }

    }

    private fun showDialog() {
        var selectedCategory = arrayListOf<String>()//선택된 항목 담음

        var builder = AlertDialog.Builder(context)?.let {
            it.setTitle("카테고리")
            //멀티 선택 이벤트
            it.setMultiChoiceItems(
                R.array.categories, null
            ) { dialog, which, isChecked -> // DialogInterface.OnMultiChoiceClickListener object의 override onClick함수 << 람다형식
                val category = resources.getStringArray(R.array.categories)//category데이터를 임시로 담아둔다

                if (isChecked) {//체크되면 추가하고 아니면 삭제
                    selectedCategory += category[which]
                } else selectedCategory -= category[which]

            }
            it.setPositiveButton("확인") { dialog, which ->
                val filteredList = ArrayList<Item>()
                for (i in items) {
                    for (j in selectedCategory) {
                        if (i.category == j) {
                            filteredList += i
                        }
                    }
                }
                if (filteredList.isEmpty()) {
                    Toast.makeText(context, "해당하는 상품이 없습니다.", Toast.LENGTH_SHORT).show()
                    adapter.setFilteredList(items)
                } else adapter.setFilteredList(filteredList)

            }

            it.setNegativeButton("닫기") { dialog, which -> // Dialoginterface.OnclieckListner...dialog는 DialogInterface>, which는 int이다
                dialog.cancel()
                adapter.setFilteredList(items)
            }

            it.show()
        }


    }

   /* companion object {
        fun newInstance(param1: String, param2: String) =
            mainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/

}