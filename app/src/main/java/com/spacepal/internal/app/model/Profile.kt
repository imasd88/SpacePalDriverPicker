package com.spacepal.internal.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.spacepal.internal.app.model.response.AddressesItem

data class Profile(
        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("firstName")
        val firstName: String? = null,

        @field:SerializedName("lastName")
        val lastName: String? = null,

        @field:SerializedName("password")
        val password: String? = null,

        @field:SerializedName("customerType")
        val customerType: String? = null,

        @field:SerializedName("addresses")
        val addresses: List<AddressesItem?>? = null,

        @field:SerializedName("phoneNumber")
        val phoneNumber: String? = null,

        @field:SerializedName("notes")
        val notes: List<String?>? = null,

        @field:SerializedName("roles")
        val roles: List<String?>? = null,

        @field:SerializedName("imageUrl")
        val imageUrl: String? = null,

        @field:SerializedName("fileUrls")
        val fileUrls: List<String?>? = null,

        @field:SerializedName("email")
        val email: String? = null,
        @Expose(serialize = false)
        var isSignIn: Boolean = false
)