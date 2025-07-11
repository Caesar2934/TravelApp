package com.example.travelapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_tickets")
data class BusTicketResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val route: String,            // Tuyến xe: Ví dụ "TP. HCM - Vĩnh Hy"
    val time: String,             // Giờ đi - đến: "8:00 AM - 13:00 PM"
    val price: Int,               // Giá vé: 270000
    val remainingSeats: Int       // Số chỗ còn lại: 15
)
{
}