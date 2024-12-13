package com.satria.gymer.network

import com.satria.gymer.network.response.ExcerciseResponse
import com.satria.gymer.network.response.DataHistories
import com.satria.gymer.network.response.DataItems
import com.satria.gymer.network.response.DetailHistoryResponse
import com.satria.gymer.network.response.ForgotPassResponse
import com.satria.gymer.network.response.LoginResp
import com.satria.gymer.network.response.PostHistory
import com.satria.gymer.network.response.RegisterResp
import com.satria.gymer.network.response.ReqBodyForgotPass
import com.satria.gymer.network.response.ReqBodyLogin
import com.satria.gymer.network.response.ReqBodyRegister
import com.satria.gymer.network.response.ReqBodyResetPass
import com.satria.gymer.network.response.ReqBodyUpdateHistory
import com.satria.gymer.network.response.ResetPassResp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Authentication
    @POST("auth/user")
    fun registerUser(@Body body: ReqBodyRegister): Call<RegisterResp>

    @POST("auth/login")
    fun loginUser(@Body body: ReqBodyLogin): Call<LoginResp>

    @POST("auth/forgot-password")
    fun forgotPassword(@Body body: ReqBodyForgotPass): Call<ForgotPassResponse>

    @POST("auth/reset-password")
    fun resetPassword(@Body body: ReqBodyResetPass): Call<ResetPassResp>

    // Exercise
    @GET("/api/excercise") // Replace with your actual endpoint
    fun getExercises(
        @Query("key") key: String = ApiConfig.API_KEY, // Add your API key if necessary
        @Query("category") category: String? = null, // Optional, add if necessary
    ): Call<ExcerciseResponse>

    @GET("exercise/{id_exercise}")
    fun getExerciseById(@Path("id_exercise") id: Int): Call<DataItems>

    // History
    @POST("history")
    fun addHistory(@Body body: PostHistory): Call<PostHistory>

    @GET("history")
    fun getHistories(): Call<List<DataHistories>>

    @GET("history/{id_history}")
    fun getHistoryById(@Path("id_history") id: Int): Call<DetailHistoryResponse>

    @PUT("history/{id_history}")
    fun updateHistory(@Path("id_history") id: Int, @Body body: ReqBodyUpdateHistory): Call<ReqBodyUpdateHistory>

    @DELETE("history/{id_history}")
    fun deleteHistory(@Path("id_history") id: Int): Call<DataHistories>
}
