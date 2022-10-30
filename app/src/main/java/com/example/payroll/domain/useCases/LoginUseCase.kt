package com.example.payroll.domain.useCases

import com.example.payroll.data.mapper.mapToPayrollDomainModel
import com.example.payroll.domain.model.EmployeeModel
import com.example.payroll.domain.model.UserCredential
import com.example.payroll.domain.repository.AuthenticationRepo
import com.example.payroll.domain.repository.UserRepo
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthenticationRepo,
    private val userRepository: UserRepo,
) {
    suspend operator fun invoke(userCredential: UserCredential): List<EmployeeModel>? {
        return runCatching {
            authRepository.login(userCredential)
                ?.let {
                    userRepository.fetchUserPayroll(it.Token)?.Payroll?.mapToPayrollDomainModel()
                        ?: emptyList()
                }
        }.getOrThrow()
    }
}
