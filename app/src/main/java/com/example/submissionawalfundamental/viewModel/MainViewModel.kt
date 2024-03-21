package com.example.submissionawalfundamental.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawalfundamental.data.response.ItemsItem
import com.example.submissionawalfundamental.data.response.SearchResponses
import com.example.submissionawalfundamental.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _userList = MutableLiveData<List<ItemsItem>?>()
    val userList : LiveData<List<ItemsItem>?> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUsers("q")
    }

    private fun findUsers(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<SearchResponses> {
            override fun onResponse(
                call: Call<SearchResponses>,
                response: Response<SearchResponses>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userList.value = responseBody.items
                    }
                } else {
                }
            }
            override fun onFailure(call: Call<SearchResponses>, t: Throwable) {
                _isLoading.value = true

            }
        })
    }

    fun searchUsers(query: String) {
        if (query.isNotBlank()) {
            findUsers(query)
        }
    }
}