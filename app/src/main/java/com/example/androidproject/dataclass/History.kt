package com.example.androidproject.dataclass

// 채팅 목록 만들 때 받아올 데이터
data class History(
    var objectId: String,
    var myId: String,
    var userId: String,
    var date: String,
    var lastMsg: String,
    var profileImgUrl: String,
    var productImgUrl: String
){
    constructor(): this(" ", " "," ", " ", " ", " ", " " )


}