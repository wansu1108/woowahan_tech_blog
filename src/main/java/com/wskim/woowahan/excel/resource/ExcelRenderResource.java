package com.wskim.woowahan.excel.resource;

import java.util.List;
import java.util.Map;

public class ExcelRenderResource {

    // TODO dataFieldName -> excelHeaderName Map Abstraction
	private Map<String, String> excelHeaderNames;
	private List<String> dataFieldNames;

    public ExcelRenderResource(Map<String,String> excelHeaderNames, List<String> dataFieldNames){
        this.dataFieldNames = dataFieldNames;
        this.excelHeaderNames = excelHeaderNames;
    }

    public String getExcelHeaderName(String fieldName){
        return excelHeaderNames.get(fieldName);
    }

    public List<String> getAllDataFieldNames(){
        return dataFieldNames;
    }
}
