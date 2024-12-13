package com.satria.gymer.data.network

import com.satria.gymer.data.model.exercise.DetailExerciseResponse
import com.satria.gymer.data.model.exercise.ExerciseResponse
import com.satria.gymer.data.model.history.AddHistoryRequest
import com.satria.gymer.data.model.history.HistoryResponse
import com.satria.gymer.data.model.forgotpassword.ForgotPasswordRequest
import com.satria.gymer.data.model.forgotpassword.ForgotPasswordResponse
import com.satria.gymer.data.model.history.DetailHistoryResponse
import com.satria.gymer.data.model.login.LoginRequest
import com.satria.gymer.data.model.login.LoginResponse
import com.satria.gymer.data.model.register.RegisterRequest
import com.satria.gymer.data.model.register.RegisterResponse
import com.satria.gymer.data.model.resetpassword.ResetPasswordRequest
import com.satria.gymer.data.model.resetpassword.ResetPasswordResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    //auth
    @POST("auth/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("auth/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/forgot-password")
    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<ForgotPasswordResponse>

    @POST("auth/reset-password")
    fun resetPassword(@Body request: ResetPasswordRequest): Call<ResetPasswordResponse>

    @POST("history")
    fun addHistory(@Body request: AddHistoryRequest): Call<HistoryResponse>

    @GET("history")
    fun getHistory(): Call<HistoryResponse>

    @GET("history/{id}")
    fun detailHistory(@Path("id") id: String): Call<DetailHistoryResponse>

    @DELETE("history/{id}")
    fun deleteHistory(@Path("id") id: String): Call<DetailHistoryResponse>

    @GET("exercise")
    fun getExercises(): Call<ExerciseResponse>

    @GET("exercise/{id}")
    fun getDetailExercises(@Path("id") id: String): Call<DetailExerciseResponse>
}