package com.example.androidproject

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.example.androidproject.databinding.FragmentRegistrationBinding
import com.example.androidproject.dataclass.Item
import com.example.androidproject.dataclass.RegisterInfo
import com.example.androidproject.viewmodel.HistoryViewModel
import com.example.androidproject.viewmodel.ItemViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class registrationFragment : Fragment(), PopupMenu.OnMenuItemClickListener {
    private val viewModel: HistoryViewModel by activityViewModels()
    private val itemViewModel : ItemViewModel by activityViewModels()//P
    private var binding: FragmentRegistrationBinding? = null
    private var category: String ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding?.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //카테고리 설정
        binding?.btnCategory?.setOnClickListener {
            showPopup(binding!!.btnCategory)
        }

        // 저장하면
        binding?.btnSave?.setOnClickListener {
            val date = getTime()

            val prod2 = Item(" ", binding?.edtTitle?.text.toString(), binding?.edtPrice?.text.toString(),
                category.toString(), " ", binding?.edtContent?.text.toString(), " ", date,
                selled = false,
                like = false
            )
            viewModel.registerProduct(prod2)

        }

    }
    // 카테고리 버튼 팝업창 띄우기
    private fun showPopup(v: View){
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(R.menu.menu_category, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }
    override fun onMenuItemClick(item: MenuItem?): Boolean {
       category = when(item?.itemId){
           R.id.homeAppliances -> "homeAppliances"
           R.id.instrument -> "instrument"
           R.id.electronics -> "electronics"
           else -> null
       }
        return item != null
   }

    // 시간 구하기
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")

        return current.format(formatter)
    }
}


