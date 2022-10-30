package com.example.payroll.domain.repository

import com.example.payroll.data.network.dto.getPayRollResponse.GetPayRollResponse


interface UserRepo {
    suspend fun fetchUserPayroll(token:String): GetPayRollResponse?
}