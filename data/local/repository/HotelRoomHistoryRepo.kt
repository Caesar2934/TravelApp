package com.example.travelapp.data.local.repository

import com.example.travelapp.data.local.dao.HotelRoomHistoryDao
import com.example.travelapp.data.local.entity.HotelRoomHistory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HotelRoomHistoryRepo @Inject constructor(
    private val dao: HotelRoomHistoryDao
) {
    fun getAll(): Flow<List<HotelRoomHistory>> = dao.getAll()
    suspend fun insert(room: HotelRoomHistory) = dao.insert(room)
}
