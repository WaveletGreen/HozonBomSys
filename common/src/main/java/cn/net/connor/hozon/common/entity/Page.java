package cn.net.connor.hozon.common.entity;

import lombok.Data;

import java.util.List;

/**
 * 分页实体类
 * */
@Data
public class Page {
	private List rows;
	private long total;
	
	public Page(){}
	
	public Page(List rows, long total) {
		super();
		this.rows = rows;
		this.total = total;
	}


	@Override
	public String toString() {
		return "Page [rows=" + rows + ", total=" + total + "]";
	}
}
