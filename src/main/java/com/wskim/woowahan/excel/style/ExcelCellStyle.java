package com.wskim.woowahan.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;

public interface ExcelCellStyle {
    void apply(CellStyle cellStyle);
}
