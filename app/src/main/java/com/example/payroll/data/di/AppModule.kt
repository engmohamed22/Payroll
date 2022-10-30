package com.example.payroll.data.di

import android.content.Context
import com.example.payroll.MyApplication

import com.example.payroll.data.gateways.ServerGateway
import com.example.payroll.data.gateways.ServerGatewayImplementer
import com.example.payroll.data.network.Endpoints
import com.example.payroll.data.repositoryImp.AuthenticationRepositoryImp
import com.example.payroll.data.repositoryImp.UserRepositoryImp
import com.example.payroll.domain.repository.AuthenticationRepo
import com.example.payroll.domain.repository.UserRepo
import com.example.payroll.domain.useCases.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApplication = app as MyApplication

    @Singleton
    @Provides
    fun provideServerGateWay(api: Endpoints): ServerGateway =
        ServerGatewayImplementer(api)


    @Provides
    @Singleton
    fun provideAuthenticationRepository(serverGateway: ServerGateway): AuthenticationRepo =
        AuthenticationRepositoryImp(serverGateway)

    @Provides
    @Singleton
    fun provideUserRepository(serverGateway: ServerGateway): UserRepo =
        UserRepositoryImp(serverGateway)

    @Provides
    @Singleton
    fun provideLoginUseCase(authenticationRepo: AuthenticationRepo, userRepo: UserRepo) =
        LoginUseCase(authenticationRepo, userRepo)


}