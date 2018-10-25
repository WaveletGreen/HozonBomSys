package com.connor.hozon.bom.resources.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 分页信息 第一页从1开始
 */
public class Page<T> implements Serializable, Iterable<T> {

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

		if (pageSize <= 0) {
			throw new IllegalArgumentException(
					"非法参数,每页显示数据不能小于0!");
		}
		this.pageSize = pageSize;
		this.pageNumber = PageUtil.computePageNumber(pageNumber, pageSize,totalCount);
		this.totalCount = totalCount;
		setResult(result);
	}

	public void setResult(List<T> elements) {

		if (elements == null)
			throw new IllegalArgumentException("暂无结果!");
		this.result = elements;
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

		return PageUtil.getFirstResult(pageNumber, pageSize);
	}

	public Iterator<T> iterator() {

		return (Iterator<T>) (result == null ? Collections.emptyList()
				.iterator() : result.iterator());
	}

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
}
