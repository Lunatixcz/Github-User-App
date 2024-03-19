package com.example.submissionawalfundamental.data.retrofit

import com.example.submissionawalfundamental.data.response.SearchResponses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") username: String
    ):Call<SearchResponses>
}