package com.example.travelapp.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val username = MutableStateFlow("")

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

    private val _registerSuccess = MutableStateFlow<Boolean?>(null)
    val registerSuccess: StateFlow<Boolean?> = _registerSuccess

    fun login() {
        if (email.value.isNotBlank() && password.value.isNotBlank()) {
            auth.signInWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    _loginSuccess.value = task.isSuccessful
                }
        }
    }

    fun register() {
        if (email.value.isNotBlank() && password.value.isNotBlank()) {
            auth.createUserWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    _registerSuccess.value = task.isSuccessful
                }
        }
    }

    fun resetLoginState() {
        _loginSuccess.value = null
    }

    fun resetRegisterState() {
        _registerSuccess.value = null
    }
}

