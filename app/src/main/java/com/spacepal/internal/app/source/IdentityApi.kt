package com.spacepal.internal.app.source

import com.spacepal.internal.app.model.response.TokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IdentityApi {

    @POST("/connect/token")
    @FormUrlEncoded
    fun getToken(@Field("username") username: String, @Field("password") password: String,
                 @Field("client_id") clientId: String,
                 @Field("client_secret") clientSecret: String,
                 @Field("grant_type") grantType: String): Call<TokenResponse>

}