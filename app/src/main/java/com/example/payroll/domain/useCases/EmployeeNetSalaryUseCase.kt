package com.example.payroll.domain.useCases

import com.example.payroll.domain.model.EmployeeModel

fun calculateEmployeeNetSalary(employeeModel: EmployeeModel): Double {

    val totalAllowance = getEmployeeTotalAllowancesUseCase(employeeModel)!!
    val totalDeductions = getEmployeeTotalDeductionsUseCase(employeeModel)!!
    return (totalAllowance - totalDeductions).roundDoubleToOnePlace()
}

fun Double.roundDoubleToOnePlace(): Double {
    return String.format("%.1f", this).toDouble()

}

fun getEmployeeTotalAllowancesUseCase(employeeModel: EmployeeModel) =
    employeeModel.allowances?.sumByDouble { it.value }

fun getEmployeeTotalDeductionsUseCase(employeeModel: EmployeeModel) =
    employeeModel.deductions?.sumByDouble { it.value }