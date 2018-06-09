package com.spacepal.internal.app.source

import com.spacepal.internal.app.model.ChangePassword
import com.spacepal.internal.app.model.EmailBody
import com.spacepal.internal.app.model.Profile
import com.spacepal.internal.app.model.Role
import com.spacepal.internal.app.model.response.AssignmentItem
import com.spacepal.internal.app.model.response.AssignmentResponse
import com.spacepal.internal.app.model.response.JobsResponse
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

    @POST("/v1/User/change-password")
    fun changePassword(@Body changePassword: ChangePassword): Call<Void>

    @GET("/v1/Assignment")
    fun getOrders(@Query("userId") userId: String,@Query("role") role: String): Call<AssignmentResponse>

    @GET("/v1/Assignment/{id}")
    fun getAssignment(@Path("id") assignmentId: String): Call<AssignmentItem>

    @GET("/v1/Job")
    fun getJobs(@Query("assignmentId") assignmentId: String): Call<JobsResponse>

    @POST("/v1/Assignment/{id}/ScanToOrder")
    fun scanToOrder(@Path("id") assignmentId: String, @Query("InventoryId") inventoryId: String): Call<Void>

    @POST("/v1/Appointment/{id}/PrintBundle")
    fun printSticker(@Path("id") appointmentId:String): Call<Void>

    @POST("/v1/Assignment/{id}/ScanBundleToBay")
    fun scanToBay(@Path("id") assignmentId: String,@Query("bayId") bayId: String): Call<Void>

}

