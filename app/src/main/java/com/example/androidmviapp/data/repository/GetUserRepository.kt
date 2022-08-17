package com.example.androidmviapp.data.repository

import com.example.androidmviapp.data.models.UserApiResponse
import com.example.androidmviapp.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserRepository @Inject constructor(
    private val apiService: ApiService,
) {

    /**
     * Function to call user api
     */
    fun getUsersApiDetails(): Flow<List<UserApiResponse>> {
        return flow {
            val result = apiService.getUserList()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}