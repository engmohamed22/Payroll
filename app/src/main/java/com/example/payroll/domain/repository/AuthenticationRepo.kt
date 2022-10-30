package com.example.payroll.domain.repository

import com.example.payroll.data.network.dto.loginResponse.LoginResponse
import com.example.payroll.domain.model.UserCredential


interface AuthenticationRepo {
    suspend fun login(userCredential: UserCredential): LoginResponse?
}