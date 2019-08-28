package cn.net.connor.hozon.common.entity;

import lombok.Data;

/*
* 类描述：查询基础类
* @auther linzf
* @create 2017/8/11 0011 
*/
@Data
public class QueryBase {
    /** 要排序的字段名 */
    protected String sort;
    /** 排序方式: desc \ asc */
    protected String order = "";
    /** 获取一页行数 */
    protected Integer limit;
    /** 获取的页码 */
    protected Integer page;
    /** 起始记录 */
    protected Integer offset;
}
