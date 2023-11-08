package com.example.androidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.androidproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val items = arrayListOf(//리스트의 길이 변경 가능
        Item("Title", "I'm selling", "Ilsan", "P1su",false, false,"none"),
        Item("Coffee", "Selled", "Ilsan", "P1su",true, false,"none"),
        Item("Guitar", "Good", "Seoul", "Joe",false, true,"none"),
        Item("Computer", "Cheap", "Busan", "Kim",false, false,"전자기기"),
        Item("Bike", "New", "Incheon", "Park",false, false,"none"),
        Item("Book", "Old", "Suwon", "Lee",false,false,"none"),
        Item("Mask", "Color is white", "Sejong", "Song",false, false,"none")
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