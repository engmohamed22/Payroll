package com.example.payroll.data.repositoryImp

import com.example.payroll.data.gateways.ServerGateway
import com.example.payroll.data.network.dto.getPayRollResponse.GetPayRollResponse
import com.example.payroll.domain.repository.UserRepo
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val serverGateway: ServerGateway
) :
    UserRepo {
    override suspend fun fetchUserPayroll(token:String): GetPayRollResponse? {
        return serverGateway.getPayroll(token)
    }
}