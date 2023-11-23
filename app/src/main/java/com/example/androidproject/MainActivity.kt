package com.example.androidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.androidproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val items = arrayListOf(//리스트의 길이 변경 가능
        Item("Title", "I'm selling", "Ilsan", "P1su",false, false,"none",50000),
        Item("Coffee", "Selled", "Ilsan", "P1su",true, false,"none",10000),
        Item("Guitar", "Good", "Seoul", "Joe",false, true,"none",200000),
        Item("Computer", "Cheap", "Busan", "Kim",false, false,"전자기기",2000000),
        Item("Bike", "New", "Incheon", "Park",false, false,"none",500000),
        Item("Book", "Old", "Suwon", "Lee",false,false,"none",5000),
        Item("Mask", "Color is white", "Sejong", "Song",false, false,"none",1000)
    )

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        binding.btmNav.setupWithNavController(navController)

        setContentView(binding.root)
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        return super.onSupportNavigateUp()
    }


}