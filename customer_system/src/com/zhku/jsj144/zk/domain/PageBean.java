package com.zhku.jsj144.zk.domain;

import java.util.List;

//用于分页的bean
//总页数，总记录数，每页记录数，当前页数，当前页数据
public class PageBean {

	private int totalPage;//总页数
	private int totalRecord;//总记录数
	private int pageRecord;//每页记录数
	private int currentPage;//当前页数
	private List<Customers> dataList;//当前页数据
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getPageRecord() {
		return pageRecord;
	}
	public void setPageRecord(int pageRecord) {
		this.pageRecord = pageRecord;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List<Customers> getDataList() {
		return dataList;
	}
	public void setDataList(List<Customers> dataList) {
		this.dataList = dataList;
	}
}
