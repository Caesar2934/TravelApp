package com.example.travelapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.travelapp.data.local.entity.Destination
import kotlinx.coroutines.flow.Flow

@Dao
interface DestinationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(destinations: List<Destination>)

    @Query("SELECT * FROM destinations")
    fun getAll(): Flow<List<Destination>>

    @Query("DELETE FROM destinations")
    suspend fun deleteAll()

    @Query("SELECT * FROM destinations")
    suspend fun getAllOnce(): List<Destination>

    @Query("UPDATE destinations SET imageUrl = :newUrl WHERE name = :name")
    suspend fun updateImageUrlByName(name: String, newUrl: String)

    @Update
    suspend fun update(destination: Destination)

    @Query("SELECT * FROM destinations WHERE name = :name LIMIT 1")
    suspend fun getDestinationByName(name: String): Destination?




}
