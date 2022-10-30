package com.example.payroll.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payroll.data.network.ResponseWrapper
import com.example.payroll.domain.model.UserCredential
import com.example.payroll.domain.useCases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _loginMutableSharedFlow = MutableSharedFlow<ResponseWrapper>()
    val loginMutableSharedFlow = _loginMutableSharedFlow.asSharedFlow()

    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch(IO) {
            if (phoneNumber.isEmpty() || password.isEmpty())
                _loginMutableSharedFlow.emit(ResponseWrapper.Error("Your phone number or password is incorrect"))
            else {
                _loginMutableSharedFlow.emit(ResponseWrapper.Loading)
                val loginResponse = loginUseCase(UserCredential(phoneNumber, password))
                loginResponse?.let { _loginMutableSharedFlow.emit(ResponseWrapper.Success(it)) }
                    ?: _loginMutableSharedFlow.emit(ResponseWrapper.Error("Something went wrong"))
            }
        }
    }
}