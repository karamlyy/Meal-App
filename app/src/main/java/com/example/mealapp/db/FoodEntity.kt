package com.example.mealapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mealapp.utils.Constant.FOOD_TABLE

@Entity(tableName = FOOD_TABLE)
data class FoodEntity(
    @PrimaryKey
    var id: Int = 0,
    var title: String = "",
    var img: String = ""
)