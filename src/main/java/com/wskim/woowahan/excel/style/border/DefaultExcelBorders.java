package com.wskim.woowahan.excel.style.border;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;

public class DefaultExcelBorders implements ExcelBorders{

    List<? extends ExcelBorder> borders;

    private DefaultExcelBorders(List<? extends ExcelBorder> borders){
        validateBorders(borders);
        this.borders = borders;
    }

    public static DefaultExcelBorders newInstance(ExcelBorderStyle style){
        List<DefaultExcelBorder> excelBorders = new ArrayList<>();
        for(int i=0; i < 4; i++){
            excelBorders.add(new DefaultExcelBorder(style));
        }
        return new DefaultExcelBorders(excelBorders);
    }

    private void validateBorders(List<? extends ExcelBorder> borders2) {
        if(borders2.size() != 4){
            throw new IllegalArgumentException("Should be initialized with TOP RIGHT LEFT BOTTOM borders");
        }
    }

    @Override
    public void apply(CellStyle cellStyle) {
        borders.get(0).applyTop(cellStyle);
        borders.get(0).applyBottom(cellStyle);
        borders.get(0).applyLeft(cellStyle);
        borders.get(0).applyRight(cellStyle);
    }
}
