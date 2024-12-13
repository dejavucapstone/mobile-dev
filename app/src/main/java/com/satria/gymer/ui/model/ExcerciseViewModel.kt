package com.satria.gymer.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.satria.gymer.network.ApiClient
import com.satria.gymer.network.ApiService
import com.satria.gymer.network.response.DataItem
import com.satria.gymer.network.response.ExcerciseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExcerciseViewModel : ViewModel() {

    private val _excerciseData = MutableLiveData<List<DataItem>?>()  // Store List<DataItem>
    val excerciseData: LiveData<List<DataItem>?> get() = _excerciseData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = "Error"
        private set

    fun getExcerciseData(category: String? = null) {
        _isLoading.value = true
        _isError.value = false

        val client = ApiClient.getClient().create(ApiService::class.java)
        val call = client.getExercises(category = category)

        // Send API request using Retrofit
        call.enqueue(object : Callback<ExcerciseResponse> {
            override fun onResponse(
                call: Call<ExcerciseResponse>,
                response: Response<ExcerciseResponse>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null || responseBody.data == null) {
                    onError("Data Processing Error")
                    return
                }

                _isLoading.value = false
                // Filter out any null items from the list before posting
                val nonNullDataItems = responseBody.data.filterNotNull()
                _excerciseData.postValue(nonNullDataItems) // Post filtered List<DataItem>
            }

            override fun onFailure(call: Call<ExcerciseResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }
        })
    }

    private fun onError(inputMessage: String?) {
        val message = if (inputMessage.isNullOrBlank()) "Unknown Error" else inputMessage
        errorMessage = "ERROR: $message. Some data may not be displayed properly."

        _isError.value = true
        _isLoading.value = false
    }
}
