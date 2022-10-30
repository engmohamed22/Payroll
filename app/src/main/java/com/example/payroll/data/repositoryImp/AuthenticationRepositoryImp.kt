package com.example.payroll.data.repositoryImp

import com.example.payroll.data.gateways.ServerGateway
import com.example.payroll.data.network.dto.loginResponse.LoginResponse
import com.example.payroll.domain.model.UserCredential
import com.example.payroll.domain.repository.AuthenticationRepo
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(private val server: ServerGateway) :
    AuthenticationRepo {
    override suspend fun login(userCredential: UserCredential): LoginResponse? {
        return server.login(userCredential)
    }


}