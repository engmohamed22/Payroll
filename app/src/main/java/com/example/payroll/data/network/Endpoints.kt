package com.example.payroll.data.network

import com.example.payroll.data.network.dto.getPayRollResponse.GetPayRollResponse
import com.example.payroll.data.network.dto.loginResponse.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface Endpoints {

    @FormUrlEncoded
    @POST("Salamtak/LogIn")
    suspend fun login(
        @Field("MobileNumber") phoneNumber: String,
        @Field("Password") Password: String,
    ): Response<LoginResponse>

    @GET("Salamtak/GetPayroll")
    suspend fun getPayroll(@Header("Authorization") token:String): Response<GetPayRollResponse>
}