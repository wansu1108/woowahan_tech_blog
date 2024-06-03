package com.wskim.woowahan.excel.style.border;

import org.apache.poi.ss.usermodel.CellStyle;

public interface ExcelBorder {
    
    public void applyTop(CellStyle cellStyle);
    public void applyBottom(CellStyle cellStyle);
    public void applyLeft(CellStyle cellStyle);
    public void applyRight(CellStyle cellStyle);
}
