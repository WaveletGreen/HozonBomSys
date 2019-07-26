package sql.pojo.bom;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/9/21
 * @Description:
 */
public class HzMbomLineRecordVO {

    private String tableName;

    private List<HzMbomLineRecord> recordList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<HzMbomLineRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<HzMbomLineRecord> recordList) {
        this.recordList = recordList;
    }
}
