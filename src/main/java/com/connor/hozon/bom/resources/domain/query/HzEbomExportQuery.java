package com.connor.hozon.bom.resources.domain.query;

import java.util.List;
import java.util.Map;

public class HzEbomExportQuery {
    /**
     * 表头
     */
    private String[] headers;
    /**
     * 选中行
     */
    private List<String[]> rows;

//    private Map<String,Object> rows ;
    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

//    public Map<String, Object> getRows() {
//        return rows;
//    }
//
//    public void setRows(Map<String, Object> rows) {
//        this.rows = rows;
//    }

        public List<String[]> getRows() {
        return rows;
    }

    public void setRows(List<String[]> rows) {
        this.rows = rows;
    }
}
