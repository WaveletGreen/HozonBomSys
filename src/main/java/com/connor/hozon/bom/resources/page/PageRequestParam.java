package com.connor.hozon.bom.resources.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求参数
 */
public class PageRequestParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 过滤条件
	 */
	private Map filters;

	/**
	 * 页码
	 */
	private int pageNumber;

	/**
	 * 页数
	 */
	private int pageSize;

	public PageRequestParam() {

		this(0, 0, null);
	}

	public PageRequestParam(int pageNumber, int pageSize) {

		this(pageNumber, pageSize, new HashMap());
	}

	public PageRequestParam(int pageNumber, int pageSize, Map filters) {

		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.filters = filters;
	}

	public Map getFilters() {

		return filters;
	}

	public void setFilters(Map filters) {

		this.filters = filters;
	}

	public int getPageNumber() {

		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {

		this.pageNumber = pageNumber;
	}

	public int getPageSize() {

		return pageSize;
	}

	public void setPageSize(int pageSize) {

		this.pageSize = pageSize;
	}

}
