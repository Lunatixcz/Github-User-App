package com.example.submissionawalfundamental.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawalfundamental.data.response.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _dataFetched = MutableLiveData<List<ItemsItem>?>()
    val dataFetched: LiveData<List<ItemsItem>?> = _dataFetched

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchData(client: Call<List<ItemsItem>>) {
        _isLoading.value = true
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val dataList = response.body()
                    _dataFetched.value = dataList
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                // Handle failure
            }
        })
    }
}