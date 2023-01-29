package com.example.a7minuteworkoutapp

data class Exercise(
    var id: Int,
    var name: String,
    var img: Int,
    var isCompleted: Boolean = false,
    var isSelected: Boolean = false,
)
