package com.example.travelapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinations")
data class Destination(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)

