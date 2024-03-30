package com.example.submissionawalfundamental.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawalfundamental.data.response.DetailUserResponse
import com.example.submissionawalfundamental.data.retrofit.ApiConfig
import com.example.submissionawalfundamental.data.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel (application: Application): ViewModel() {

    private val _detail = MutableLiveData<DetailUserResponse?>()
    val detail: LiveData<DetailUserResponse?> = _detail

    private val _username = MutableLiveData<String?>()
    val username: LiveData<String?> = _username

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)

    fun findDetail(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
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
                _isLoading.value = false
                errorMessage.value = "Failed to find detail"
            }
        })
    }

    fun checkIfFavorite (username: String): LiveData<Boolean> {
        return mFavoriteRepository.checkIfFavorite(username)
    }
}