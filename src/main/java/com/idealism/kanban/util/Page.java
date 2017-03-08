package com.idealism.kanban.util;

public class Page {
	private int pageSize;
	private int rowCount;
	private int currentPage;
	private String OrderByField;
	private String OrderBySort;
	private int search;

	public Page() {
		this.pageSize = 15;
		this.currentPage = 0;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getOrderByField() {
		return OrderByField;
	}

	public void setOrderByField(String orderByField) {
		OrderByField = orderByField;
	}

	public String getOrderBySort() {
		return OrderBySort;
	}

	public void setOrderBySort(String orderBySort) {
		OrderBySort = orderBySort;
	}

	public int getFirstResult() {
		return this.pageSize * this.currentPage;
	}

	public int getSearch() {
		return search;
	}

	public void setSearch(int search) {
		this.search = search;
	}

}