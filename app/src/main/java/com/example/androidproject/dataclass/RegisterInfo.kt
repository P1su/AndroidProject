package com.example.androidproject.dataclass

//상품 등록할 때 저장할 데이터

data class RegisterInfo(
    var userId: String,
    var title: String,
    var price: String,
    var type: String,
    var imgUrl: String,
    var content: String,
    var location: String,
    var date: String

){
    constructor(): this(" ", " ", " ", " ", " ", " ", " ", " ")
}