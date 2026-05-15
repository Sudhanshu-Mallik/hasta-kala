package com.hastakala.shop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val price: Double,
    val quantity: Int,
    val imageUri: String? = null // For simplicity, storing URI as String
)
