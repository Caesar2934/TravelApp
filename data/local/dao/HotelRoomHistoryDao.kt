package com.example.travelapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.travelapp.data.local.entity.HotelRoomHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelRoomHistoryDao {
    @Query("SELECT * FROM hotel_room_history")
    fun getAll(): Flow<List<HotelRoomHistory>>

    @Insert
    suspend fun insert(room: HotelRoomHistory)
}
