package com.example.travelapp.data.local.repository

import com.example.travelapp.data.local.database.AppDatabase
import com.example.travelapp.data.local.entity.BusTicketHistory
import com.example.travelapp.data.local.entity.HotelRoomHistory
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class BookingHistoryRepository @Inject constructor(
    private val db: AppDatabase
) {
    val busHistory: Flow<List<BusTicketHistory>> = db.busTicketHistoryDao().getAll()
    val roomHistory: Flow<List<HotelRoomHistory>> = db.hotelRoomHistoryDao().getAll()

    suspend fun insertBus(ticket: BusTicketHistory) = db.busTicketHistoryDao().insert(ticket)
    suspend fun insertRoom(room: HotelRoomHistory) = db.hotelRoomHistoryDao().insert(room)
}
