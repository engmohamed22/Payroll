package com.example.payroll.domain.model

import java.io.Serializable

data class EmployeeModel(
    val employeeName: String? = null,
    val JobTitle: String? = null,
    val netSalary: String? = null,
    val Date: String? = null,
    val allowances: List<Allowance>? = null,
    val deductions: List<Deduction>? = null,
    val currency: String? = null,
) : Serializable

data class Allowance(
    val description: String,
    val value: Double
) : Serializable

data class Deduction(
    val description: String,
    val value: Double
) : Serializable