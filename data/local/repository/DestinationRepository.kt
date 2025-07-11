package com.example.travelapp.data.local.repository

import com.example.travelapp.data.local.dao.DestinationDao
import com.example.travelapp.data.local.entity.Destination
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DestinationRepository @Inject constructor(
    private val dao: DestinationDao
) {
    fun getAll(): Flow<List<Destination>> = dao.getAll()
    suspend fun insertAll(list: List<Destination>) = dao.insertAll(list)
    suspend fun resetAllDestinations(newList: List<Destination>) {
        dao.deleteAll()
        dao.insertAll(newList)
    }

    suspend fun updateImageUrlByName(name: String, newUrl: String) {
        dao.updateImageUrlByName(name, newUrl)
    }

    suspend fun updateDestination(destination: Destination) = dao.update(destination)

    suspend fun getDestinationByName(name: String): Destination? {
        return dao.getDestinationByName(name)
    }



}


