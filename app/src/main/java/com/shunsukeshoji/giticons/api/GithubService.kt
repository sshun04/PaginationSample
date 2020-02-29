package com.shunsukeshoji.giticons.api

import com.shunsukeshoji.giticons.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import kotlin.math.sin

fun loadUser(
    service: GithubService,
    since: Int,
    onSuccess: (users: List<User>) -> Unit,
    onError: (error: String) -> Unit
) {
    service.getUser(since).enqueue(object : Callback<List<User>> {
        override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
            if (response.isSuccessful) {
                val users = response.body() ?: emptyList()
                onSuccess(users)
            } else {
                onError(response.errorBody()?.string() ?: "Unknown error")
            }
        }

        override fun onFailure(call: Call<List<User>>, t: Throwable) {
            onError(t.message ?: "unknown error")
        }
    })
}

interface GithubService {
    @GET(value = "users")
    fun getUser(@Query(value = "since") id: Int): Call<List<User>>
}