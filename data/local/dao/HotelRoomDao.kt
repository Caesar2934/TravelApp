package com.example.travelapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.travelapp.data.local.entity.HotelRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelRoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(room: HotelRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rooms: List<HotelRoom>)

    @Query("SELECT * FROM hotel_rooms")
    fun getAllRooms(): Flow<List<HotelRoom>>

    @Query("SELECT * FROM hotel_rooms WHERE location = :location")
    fun searchRooms(location: String): Flow<List<HotelRoom>>

    @Query("SELECT * FROM hotel_rooms WHERE location = :location")
    suspend fun getRoomsByLocation(location: String): List<HotelRoom>
}

