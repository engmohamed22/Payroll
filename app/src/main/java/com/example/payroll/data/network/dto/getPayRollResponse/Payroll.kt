package com.example.payroll.data.network.dto.getPayRollResponse

import com.example.payroll.data.network.dto.getPayRollResponse.AllowanceDto
import com.example.payroll.data.network.dto.getPayRollResponse.DeductionDTO
import com.example.payroll.data.network.dto.getPayRollResponse.Employee
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Payroll(
    @SerializedName("Allowences")
    val allowancesDto: List<AllowanceDto>,
    val Date: String,
    @SerializedName("Deduction")
    val DeductionsDTO: List<DeductionDTO>,
    val Employee: List<Employee>
):Serializable