package com.example.travelapp.data.local.repository

import com.example.travelapp.data.local.dao.HotelRoomDao
import com.example.travelapp.data.local.entity.HotelRoom
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HotelRoomRepository @Inject constructor(
    private val dao: HotelRoomDao
) {
    fun getAllRooms(): Flow<List<HotelRoom>> = dao.getAllRooms()
    suspend fun insertRooms(rooms: List<HotelRoom>) = dao.insertAll(rooms)
    fun searchRooms(location: String): Flow<List<HotelRoom>> = dao.searchRooms(location)

    suspend fun getRoomsByLocation(location: String): List<HotelRoom> = dao.getRoomsByLocation(location)
}

