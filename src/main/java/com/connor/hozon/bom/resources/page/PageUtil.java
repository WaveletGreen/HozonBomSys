package com.connor.hozon.bom.resources.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageUtil {

	private PageUtil() {

	}

	public static int getFirstResult(int pageNumber, int pageSize) {

		if (pageSize <= 0)
			throw new IllegalArgumentException(
					"非法参数,每页显示数据不能小于0!");
		return (pageNumber - 1) * pageSize;
	}

	public static int computeLastPageNumber(int totalElements, int pageSize) {

		int result = totalElements % pageSize == 0 ? totalElements / pageSize
				: totalElements / pageSize + 1;
		if (result <= 1)
			result = 1;
		return result;
	}

	public static int computePageNumber(int pageNumber, int pageSize,
			int totalElements) {

		if (pageNumber <= 1) {
			return 1;
		}
		if (Integer.MAX_VALUE == pageNumber
				|| pageNumber > computeLastPageNumber(totalElements, pageSize)) {
			return computeLastPageNumber(totalElements, pageSize);
		}
		return pageNumber;
	}

	/**
	 * 根据List 返回当前页结果
	 * @param pageNumber
	 * @param pageSize
	 * @param dataList
	 * @return
	 */
	public static List<?> getResult(int pageNumber, int pageSize, List<?> dataList) {
		if(dataList==null || dataList.size()==0){
			return Collections.EMPTY_LIST;
		}
		List<Object> result = new ArrayList<Object>() ;
		int len = dataList.size();
		int minValue = (pageNumber-1)*pageSize ; 
		int maxValue = (pageNumber-1)*pageSize+pageSize ; 
		if(maxValue>len){
			maxValue = len ; 
		}
		
		for(int i = minValue; i<maxValue ;i++){
			result.add(dataList.get(i)) ; 
		}
		return result ; 
	}
}
