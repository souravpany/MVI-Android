package com.example.androidmviapp.network

import com.example.androidmviapp.data.models.UserApiResponse
import retrofit2.http.*

interface ApiService {

    @GET(EndPoint.USERS)
    suspend fun getUserList(): UserApiResponse

}