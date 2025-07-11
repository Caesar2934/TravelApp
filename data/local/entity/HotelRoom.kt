package com.example.travelapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hotel_rooms")
data class HotelRoom(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val bedType: String,
    val size: String,
    val price: Int,
    val features: String,
    val location: String
)
