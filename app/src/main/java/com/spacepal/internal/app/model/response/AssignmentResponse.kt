package com.spacepal.internal.app.model.response

import com.google.gson.annotations.SerializedName

open class AssignmentResponse {

	@SerializedName("totalItems")
	val totalItems: Int? = null

	@field:SerializedName("itemsPerPage")
	val itemsPerPage: Int? = null

	@field:SerializedName("totalPages")
	val totalPages: Int? = null

	@field:SerializedName("currentPage")
	val currentPage: Int? = null

	@field:SerializedName("items")
	val items: List<AssignmentItem>? = null

}
