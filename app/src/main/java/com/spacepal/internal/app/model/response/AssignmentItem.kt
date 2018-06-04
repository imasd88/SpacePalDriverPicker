package com.spacepal.internal.app.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

  class AssignmentItem:Serializable {

	  @field:SerializedName("googleAddress")
	  val googleAddress: String? = null

	  @field:SerializedName("role")
	  val role: String? = null

	  @field:SerializedName("bayTitle")
	  val bayTitle: String? = null

	  @field:SerializedName("prevAssignmentId")
	  val prevAssignmentId: String? = null

	  @field:SerializedName("bayId")
	  val bayId: String? = null

	  @field:SerializedName("userFullName")
	  val userFullName: String? = null

	  @field:SerializedName("createdDateTimeUtc")
	  val createdDateTimeUtc: String? = null

	  @field:SerializedName("priority")
	  val priority: Int? = null

	  @field:SerializedName("userId")
	  val userId: String? = null

	  @field:SerializedName("assignmentType")
	  val assignmentType: String? = null

	  @field:SerializedName("isEditable")
	  val isEditable: Boolean? = null

	  @field:SerializedName("appointmentId")
	  val appointmentId: String? = null

	  @field:SerializedName("isAccepted")
	  val isAccepted: Boolean? = null

	  @field:SerializedName("id")
	  val id: String? = null

	  @field:SerializedName("dueDateTimeUtc")
	  val dueDateTimeUtc: String? = null

	  @field:SerializedName("isComplete")
	  val isComplete: Boolean? = null
  }