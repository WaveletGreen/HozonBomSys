/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 */
public class Page<T> {

	protected List<T> result;

	protected int pageSize;

	protected int pageNumber;

    protected int totalCount;

	public Page() {

	}

	public Page(PageRequestParam p, int totalCount) {

		this(p.getPageNumber(), p.getPageSize(), totalCount);
	}

	public Page(int pageNumber, int pageSize, int totalCount) {

		this(pageNumber, pageSize, totalCount, new ArrayList(0));
	}

	public Page(int pageNumber, int pageSize, int totalCount, List<T> result) {
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.totalCount = totalCount;
		setResult(result);
	}

	public void setResult(List<T> list) {
		this.result = list;
	}

	public List<T> getResult() {

		return result;
	}

	public int getTotalCount() {

		return totalCount;
	}

	public int getPageSize() {

		return pageSize;
	}


	public int getPageNumber() {

		return pageNumber;
	}

	public int getFirstResult() {
		return (pageNumber - 1) * pageSize;
	}


    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
}
