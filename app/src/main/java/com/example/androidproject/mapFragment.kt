package com.example.androidproject

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.androidproject.R.id
import com.example.androidproject.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class mapFragment : Fragment(),OnMapReadyCallback {

    private lateinit var gMap: GoogleMap
    private lateinit var mView: MapView
    private lateinit var rootview: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    var binding : FragmentMapBinding ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater)
        mView = binding?.map as MapView
        mView?.let{
            it.onCreate(savedInstanceState)
            it.onResume()
            it.getMapAsync(this)
        }

        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnTohome?.setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_mainFragment)

        }
        binding?.btnCategory?.setOnClickListener {
            showDialog()
        }

    }

    override fun onMapReady(p0: GoogleMap) {
        gMap =p0
        val marker = LatLng(37.60083910497026,126.86399629249507)
        gMap.addMarker(MarkerOptions().position(marker).title("현재 위치"))
        gMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
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


            }

            it.setNegativeButton("닫기") { dialog, which -> // Dialoginterface.OnclieckListner...dialog는 DialogInterface>, which는 int이다
                dialog.cancel()

            }

            it.show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}