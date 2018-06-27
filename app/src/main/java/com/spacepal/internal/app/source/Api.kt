package com.spacepal.internal.app.source

import com.spacepal.internal.app.model.ChangePassword
import com.spacepal.internal.app.model.EmailBody
import com.spacepal.internal.app.model.Profile
import com.spacepal.internal.app.model.Role
import com.spacepal.internal.app.model.response.AssignmentItem
import com.spacepal.internal.app.model.response.AssignmentResponse
import com.spacepal.internal.app.model.response.JobsResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    /*******************Roles */

    @get:GET("/v1/Role")
    val roles: Call<List<Role>>

    @get:GET("/v1/User/Me")
    val account: Call<Profile>

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

    @POST("/v1/Assignment/{id}/PrintBundle")
    fun printSticker(@Path("id") appointmentId:String): Call<Void>

    @POST("/v1/Assignment/{id}/ScanBundleToBay")
    fun scanToBay(@Path("id") assignmentId: String,@Query("bayId") bayId: String): Call<Void>

    @POST("/v1/Assignment/{id}/ScanToTrolly")
    fun scanToTrolly(@Path("id") assignmentId: String, @Query("InventoryId") inventoryId: String): Call<Void>

    @POST("/v1/Assignment/{id}/ScanTrollyToBay")
    fun scanTrollyToBay(@Path("id") assignmentId: String, @Query("InventoryId") inventoryId: String, @Query("bayId") bayId: String): Call<Void>

    @POST("/v1/Assignment/{id}/ScanTrollyToShelf")
    fun scanTrollyToShelf(@Path("id") assignmentId: String, @Query("InventoryId") inventoryId: String, @Query("shelfId") shelfId: String): Call<Void>

}

