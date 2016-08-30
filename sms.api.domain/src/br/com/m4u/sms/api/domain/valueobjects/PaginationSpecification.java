package br.com.m4u.sms.api.domain.valueobjects;

public final class PaginationSpecification {
	private int currentPage;
	private int pageMaxRows;
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageMaxRows() {
		return pageMaxRows;
	}
	public void setPageMaxRows(int pageMaxRows) {
		this.pageMaxRows = pageMaxRows;
	}
}
