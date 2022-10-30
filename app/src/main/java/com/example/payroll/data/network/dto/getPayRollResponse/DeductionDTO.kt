package com.example.payroll.data.network.dto.getPayRollResponse

import java.io.Serializable

data class DeductionDTO(
    val COMP_DESC_AR: String,
    val COMP_DESC_EN: String,
    val EMP_ID: Int,
    val SAL_COMP_CODE: Int,
    val SAL_COMP_TYPE: Int,
    val SAL_VALUE: Double
):Serializable