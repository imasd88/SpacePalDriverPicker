package com.spacepal.internal.app.source

import com.spacepal.internal.app.model.ChangePassword
import com.spacepal.internal.app.model.EmailBody
import com.spacepal.internal.app.model.Profile
import com.spacepal.internal.app.model.Role
import com.spacepal.internal.app.model.response.Order
import com.spacepal.internal.app.model.response.TokenResponse
import retrofit2.Call
import retrofit2.http.*

interface API {

    /*******************Roles */

    @get:GET("/v1/Role")
    val roles: Call<List<Role>>

    @get:GET("/v1/User/Me")
    val account: Call<Profile>

    @POST("/connect/token")
    @FormUrlEncoded
    fun getToken(@Field("username") username: String, @Field("password") password: String,
                 @Field("client_id") clientId: String,
                 @Field("client_secret") clientSecret: String,
                 @Field("grant_type") grantType: String): Call<TokenResponse>


    @POST("/v1/User/forgot-password")
    fun forgotPass(@Body email: EmailBody): Call<Void>

    /** */

    /********************* Account  */
//    @POST("/v1/Users")
//    fun createAccount(@Body user: User): Call<User>

    @POST("/v1/Users")
    fun updateAccount(@Body profile: Profile): Call<Void>

    @GET("/v1/Order/Dash")
    fun getOrders(@Query("role") role: String): Call<List<Order>>

    @POST("/v1/User/change-password")
    fun changePassword(@Body changePassword: ChangePassword): Call<Void>


}

