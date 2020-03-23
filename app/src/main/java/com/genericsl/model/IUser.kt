package com.genericsl.model

interface IUser {
    val email:String
    val password:String
    fun isDataValid():Int
    //val isDataValid:Boolean
}