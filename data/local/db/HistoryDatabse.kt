package com.example.travelapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.travelapp.data.local.dao.BusTicketHistoryDao
import com.example.travelapp.data.local.dao.HotelRoomHistoryDao
import com.example.travelapp.data.local.entity.BusTicketHistory
import com.example.travelapp.data.local.entity.HotelRoomHistory

@Database(
    entities = [BusTicketHistory::class, HotelRoomHistory::class],
    version = 1,
    exportSchema = false
)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun busTicketHistoryDao(): BusTicketHistoryDao
    abstract fun hotelRoomHistoryDao(): HotelRoomHistoryDao
}
