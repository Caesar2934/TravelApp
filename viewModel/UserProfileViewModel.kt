package com.example.travelapp.viewModel


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.local.repository.UserRepo
import com.example.travelapp.data.local.entity.UserEntity
import com.example.travelapp.model.MenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserProfileUiState(
    val user: UserEntity = UserEntity(),
    val menuItems: List<MenuItem> = emptyList(),
    val selectedBottomNavItem: Int = 0,
    val isLoading: Boolean = false,
    val lastMenuClicked: String? = null
)

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val repository: UserRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getUser().collect { user ->
                if (user != null) {
                    _uiState.update {
                        it.copy(
                            user = user,
                            menuItems = defaultMenuItems()
                        )
                    }
                } else {

                    val defaultUser = UserEntity(
                        id = 1,
                        email = "monalisarz2934@gmail.com",
                        username = "Sar"
                    )
                    repository.insertUser(defaultUser)

                    _uiState.update {
                        it.copy(
                            user = defaultUser,
                            menuItems = defaultMenuItems()
                        )
                    }
                }
            }
        }
    }

    fun onMenuItemClick(id: String) {
        _uiState.update { it.copy(lastMenuClicked = id) }
    }

    fun clearLastMenuClicked() {
        _uiState.update { it.copy(lastMenuClicked = null) }
    }

    fun onBottomNavItemClick(index: Int) {
        _uiState.update { it.copy(selectedBottomNavItem = index) }
    }

    private fun defaultMenuItems(): List<MenuItem> = listOf(
        MenuItem("account", "Tài khoản", Icons.Default.AccountCircle),
        MenuItem("notification", "Thông báo", Icons.Default.Notifications),
        MenuItem("language", "Ngôn ngữ", Icons.Default.Language),
        MenuItem("help", "Trợ giúp", Icons.Default.Help),
        MenuItem("settings", "Cài đặt tài khoản", Icons.Default.Settings),
        MenuItem("history", "Lịch sử mua", Icons.Default.History),
        MenuItem("logout", "Đăng xuất", Icons.Default.ExitToApp)
    )


    private fun navigateToAccount() {
        println("Navigate to Account")
    }

    private fun navigateToNotification() {
        println("Navigate to Notification")
    }

    private fun navigateToLanguage() {
        println("Navigate to Language")
    }

    private fun navigateToHelp() {
        println("Navigate to Help")
    }

    private fun navigateToSettings() {
        println("Navigate to Settings")
    }

    private fun navigateToHistory() {
        println("Navigate to History")
    }

    private fun handleLogout() {
        println("Logout")
    }

    fun updateUser(user: UserEntity) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }
}
