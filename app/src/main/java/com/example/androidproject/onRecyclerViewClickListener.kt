package com.example.androidproject

import android.view.View

interface OnRecyclerViewClickListener {
    fun onLikeClick(value : Boolean, title: String)
    fun onViewClick(view: View, pos : Int)
}