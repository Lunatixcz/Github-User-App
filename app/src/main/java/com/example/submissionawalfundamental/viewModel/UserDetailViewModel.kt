package com.example.submissionawalfundamental.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawalfundamental.data.response.DetailUserResponse
import com.example.submissionawalfundamental.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    private val _detail = MutableLiveData<DetailUserResponse?>()
    val detail: LiveData<DetailUserResponse?> = _detail

    private val _username = MutableLiveData<String?>()
    val username: LiveData<String?> = _username

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun findDetail(username: String){
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detail.value = responseBody
                    }
                } else {
                    errorMessage.value = "Detail not Available"
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                errorMessage.value = "Failed to find detail"
            }
        })
    }
}