package com.example.androidproject

data class Item(
    var title: String="",
    var body: String="",
    var location: String="",
    var id: String="",
    var selled: Boolean=false,
    var like: Boolean=false,
    var category: String="",
    var price: Int=0

)