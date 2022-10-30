package com.example.payroll.data.gateways

import com.example.payroll.data.network.Endpoints
import com.example.payroll.data.network.dto.getPayRollResponse.GetPayRollResponse
import com.example.payroll.data.network.dto.loginResponse.LoginResponse
import com.example.payroll.domain.model.UserCredential
import javax.inject.Inject


class ServerGatewayImplementer @Inject constructor(
    private val api: Endpoints
) :
    ServerGateway {


    override suspend fun getPayroll(token: String): GetPayRollResponse? {
        return api.getPayroll("Bearer $token").body()
    }

    override suspend fun login(userCredential: UserCredential): LoginResponse? {
        return api.login(userCredential.phoneNumber, userCredential.password).body()
    }
}

interface ServerGateway {

    suspend fun login(userCredential: UserCredential): LoginResponse?
    suspend fun getPayroll(token: String): GetPayRollResponse?
}