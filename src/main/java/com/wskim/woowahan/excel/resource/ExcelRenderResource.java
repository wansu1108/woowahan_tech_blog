package com.wskim.woowahan.excel.resource;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;

import com.wskim.woowahan.excel.resource.collection.PreCalculatedCellStyleMap;

public class ExcelRenderResource {

    private PreCalculatedCellStyleMap styleMap;

    // TODO dataFieldName -> excelHeaderName Map Abstraction
	private Map<String, String> excelHeaderNames;
	private List<String> dataFieldNames;

    public ExcelRenderResource(Map<String,String> excelHeaderNames, List<String> dataFieldNames, PreCalculatedCellStyleMap styleMap){
        this.dataFieldNames = dataFieldNames;
        this.excelHeaderNames = excelHeaderNames;
        this.styleMap = styleMap;
    }

    public CellStyle getCellStyle(String dataFieldName ,ExcelRenderLocation renderLocation){
        return styleMap.get(ExcelCellKey.of(dataFieldName, renderLocation));
    }

    public String getExcelHeaderName(String fieldName){
        return excelHeaderNames.get(fieldName);
    }

    public List<String> getAllDataFieldNames(){
        return dataFieldNames;
    }
}
