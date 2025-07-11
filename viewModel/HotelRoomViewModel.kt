package com.example.travelapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.local.db.AppPreferences
import com.example.travelapp.data.local.entity.HotelRoom
import com.example.travelapp.data.local.repository.HotelRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelRoomViewModel @Inject constructor(
    private val repository: HotelRoomRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    fun checkAndInsertSampleRooms() {
        viewModelScope.launch {
            val currentRooms = repository.getAllRooms().first()
            if (currentRooms.isEmpty()) {
                appPreferences.clearAllProvinces()
                insertSampleRoomsForAllProvinces()
            }
        }
    }

    init {
        checkAndInsertSampleRooms()
    }


    private val _searchResults = MutableStateFlow<List<HotelRoom>>(emptyList())
    val searchResults: StateFlow<List<HotelRoom>> = _searchResults

    fun searchRoomsByLocation(location: String) {
        viewModelScope.launch {
            _searchResults.value = repository.getRoomsByLocation(location)
        }
    }

    fun saveRooms(rooms: List<HotelRoom>) {
        viewModelScope.launch {
            repository.insertRooms(rooms)
        }
    }

    fun insertSampleRoomsForAllProvinces() {
        val provinces = listOf(
            "Hà Nội", "TP.HCM", "Hội An", "Sapa", "Cần Thơ",
            "Huế", "Ninh Thuận", "Nha Trang", "Tây Ninh", "Phú Quốc",
            "Ninh Bình", "Bình Thuận", "Vịnh Hạ Long", "Đà Nẵng", "Mộc Châu"
        )

        viewModelScope.launch {
            provinces.forEach { province ->
                if (!appPreferences.isProvinceInserted(province)) {
                    val rooms = List(6) { index ->
                        HotelRoom(
                            name = "Phòng $province ${index + 1}",
                            bedType = if (index % 2 == 0) "1 giường đôi" else "2 giường đơn",
                            size = "${30 + index * 2} mét vuông",
                            price = 500000 + index * 100000,
                            features = "Wi-Fi, Máy lạnh, Tivi, Phòng tắm riêng",
                            location = province
                        )
                    }
                    repository.insertRooms(rooms)
                    appPreferences.addInsertedProvince(province)
                }
            }
        }
    }

}


