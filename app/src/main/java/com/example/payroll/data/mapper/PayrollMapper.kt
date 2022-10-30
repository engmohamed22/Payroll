package com.example.payroll.data.mapper

import com.example.payroll.data.network.dto.getPayRollResponse.AllowanceDto
import com.example.payroll.data.network.dto.getPayRollResponse.DeductionDTO
import com.example.payroll.data.network.dto.getPayRollResponse.Payroll
import com.example.payroll.domain.model.Allowance
import com.example.payroll.domain.model.Deduction
import com.example.payroll.domain.model.EmployeeModel

fun Payroll.mapToPayrollDomainModel(): List<EmployeeModel> {
    val list = ArrayList<EmployeeModel>()
    for (employee in this.Employee) {
        list.add(
            EmployeeModel(
                allowances = this.allowancesDto.mapToAllowanceDomainModel(),
                deductions = this.DeductionsDTO.mapToDeductionDomainModel(),
                employeeName = employee.EMP_DATA_AR,
                Date = this.Date,
                JobTitle = employee.JOBNAME_AR,
                currency = employee.CURRSYMBOL_AR
            )
        )

    }
    return list
}

fun List<AllowanceDto>.mapToAllowanceDomainModel(): List<Allowance> {
    return this.map {
        Allowance(it.COMP_DESC_AR, value = it.SAL_VALUE)
    }

}

fun List<DeductionDTO>.mapToDeductionDomainModel(): List<Deduction> {
    return this.map {
        Deduction(description = it.COMP_DESC_AR, value = it.SAL_VALUE)
    }
}

