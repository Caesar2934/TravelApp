package com.example.travelapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_tickets")
data class BusTicket(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val route: String,
    val time: String,
    val price: Int,
    val remainingSeats: Int,
    val busType: String
)
