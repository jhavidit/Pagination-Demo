package tech.jhavidit.payOAssignment.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tech.jhavidit.payOAssignment.models.UserDetails

interface APIInterface {
    @GET("users")
    suspend fun fetchUsers(
        @Query("page") pageNumber: Int
    ): Response<UserDetails>
}