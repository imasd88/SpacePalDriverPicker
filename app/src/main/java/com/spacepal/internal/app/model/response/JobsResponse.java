package com.spacepal.internal.app.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class JobsResponse{

	@SerializedName("totalItems")
	private int totalItems;

	@SerializedName("itemsPerPage")
	private int itemsPerPage;

	@SerializedName("totalPages")
	private int totalPages;

	@SerializedName("currentPage")
	private int currentPage;

	@SerializedName("items")
	private List<JobItem> job;

	public void setTotalItems(int totalItems){
		this.totalItems = totalItems;
	}

	public int getTotalItems(){
		return totalItems;
	}

	public void setItemsPerPage(int itemsPerPage){
		this.itemsPerPage = itemsPerPage;
	}

	public int getItemsPerPage(){
		return itemsPerPage;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}

	public int getCurrentPage(){
		return currentPage;
	}

	public void setJob(List<JobItem> job){
		this.job = job;
	}

	public List<JobItem> getJob(){
		return job;
	}

	@Override
 	public String toString(){
		return 
			"JobsResponse{" + 
			"totalItems = '" + totalItems + '\'' + 
			",itemsPerPage = '" + itemsPerPage + '\'' + 
			",totalPages = '" + totalPages + '\'' + 
			",currentPage = '" + currentPage + '\'' + 
			",job = '" + job + '\'' + 
			"}";
		}
}