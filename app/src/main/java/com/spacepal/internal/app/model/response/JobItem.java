package com.spacepal.internal.app.model.response;

import com.google.gson.annotations.SerializedName;


public class JobItem{

	@SerializedName("shelfId")
	private String shelfId;

	@SerializedName("orderId")
	private String orderId;

	@SerializedName("bayTitle")
	private String bayTitle;

	@SerializedName("bayId")
	private String bayId;

	@SerializedName("createdDateTimeUtc")
	private String createdDateTimeUtc;

	@SerializedName("shelfTitle")
	private String shelfTitle;

	@SerializedName("assignmentId")
	private String assignmentId;

	@SerializedName("assignmentType")
	private String assignmentType;

	@SerializedName("productTitle")
	private String productTitle;

	@SerializedName("primaryUserFullName")
	private String primaryUserFullName;

	@SerializedName("completedDateTimeUtc")
	private String completedDateTimeUtc;

	@SerializedName("appointmentId")
	private String appointmentId;

	@SerializedName("inventoryId")
	private String inventoryId;

	@SerializedName("id")
	private String id;

	@SerializedName("primaryUserId")
	private String primaryUserId;

	public void setShelfId(String shelfId){
		this.shelfId = shelfId;
	}

	public String getShelfId(){
		return shelfId;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setBayTitle(String bayTitle){
		this.bayTitle = bayTitle;
	}

	public String getBayTitle(){
		return bayTitle;
	}

	public void setBayId(String bayId){
		this.bayId = bayId;
	}

	public String getBayId(){
		return bayId;
	}

	public void setCreatedDateTimeUtc(String createdDateTimeUtc){
		this.createdDateTimeUtc = createdDateTimeUtc;
	}

	public String getCreatedDateTimeUtc(){
		return createdDateTimeUtc;
	}

	public void setShelfTitle(String shelfTitle){
		this.shelfTitle = shelfTitle;
	}

	public String getShelfTitle(){
		return shelfTitle;
	}

	public void setAssignmentId(String assignmentId){
		this.assignmentId = assignmentId;
	}

	public String getAssignmentId(){
		return assignmentId;
	}

	public void setAssignmentType(String assignmentType){
		this.assignmentType = assignmentType;
	}

	public String getAssignmentType(){
		return assignmentType;
	}

	public void setProductTitle(String productTitle){
		this.productTitle = productTitle;
	}

	public String getProductTitle(){
		return productTitle;
	}

	public void setPrimaryUserFullName(String primaryUserFullName){
		this.primaryUserFullName = primaryUserFullName;
	}

	public String getPrimaryUserFullName(){
		return primaryUserFullName;
	}

	public void setCompletedDateTimeUtc(String completedDateTimeUtc){
		this.completedDateTimeUtc = completedDateTimeUtc;
	}

	public String getCompletedDateTimeUtc(){
		return completedDateTimeUtc;
	}

	public void setAppointmentId(String appointmentId){
		this.appointmentId = appointmentId;
	}

	public String getAppointmentId(){
		return appointmentId;
	}

	public void setInventoryId(String inventoryId){
		this.inventoryId = inventoryId;
	}

	public String getInventoryId(){
		return inventoryId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPrimaryUserId(String primaryUserId){
		this.primaryUserId = primaryUserId;
	}

	public String getPrimaryUserId(){
		return primaryUserId;
	}

	@Override
 	public String toString(){
		return 
			"JobItem{" + 
			"shelfId = '" + shelfId + '\'' + 
			",orderId = '" + orderId + '\'' + 
			",bayTitle = '" + bayTitle + '\'' + 
			",bayId = '" + bayId + '\'' + 
			",createdDateTimeUtc = '" + createdDateTimeUtc + '\'' + 
			",shelfTitle = '" + shelfTitle + '\'' + 
			",assignmentId = '" + assignmentId + '\'' + 
			",assignmentType = '" + assignmentType + '\'' + 
			",productTitle = '" + productTitle + '\'' + 
			",primaryUserFullName = '" + primaryUserFullName + '\'' + 
			",completedDateTimeUtc = '" + completedDateTimeUtc + '\'' + 
			",appointmentId = '" + appointmentId + '\'' + 
			",inventoryId = '" + inventoryId + '\'' + 
			",id = '" + id + '\'' + 
			",primaryUserId = '" + primaryUserId + '\'' + 
			"}";
		}
}