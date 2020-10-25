package com.sunnysouth.repository.models

import java.util.*

class Product(
    val title:String, val subtitle:String = "", val timeOrder:String = "",
    val details:String = "", val type_delivery:String = "", val img:Int = 0, val score:Double = 0.0,
    val price:Double = 0.0
)