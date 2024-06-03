package com.wskim.woowahan.excel.resource.collection;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.wskim.woowahan.excel.resource.ExcelCellKey;
import com.wskim.woowahan.excel.style.ExcelCellStyle;

public class PreCalculatedCellStyleMap {
    
    private final Map<ExcelCellKey, CellStyle> cellStyleMap = new HashMap<>();

    public void put(ExcelCellKey excelCellKey, ExcelCellStyle excelCellStyle, Workbook wb){
        CellStyle cellStyle = wb.createCellStyle();
        excelCellStyle.apply(cellStyle);
        cellStyleMap.put(excelCellKey, cellStyle);
    }

    public CellStyle get(ExcelCellKey excelCellKey){
        return cellStyleMap.get(excelCellKey);
    }

    public boolean isEmpty(){
        return cellStyleMap.isEmpty();
    }
}
