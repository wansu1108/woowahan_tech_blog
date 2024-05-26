package com.wskim.woowahan.excel.style.configurer;

import org.apache.poi.ss.usermodel.CellStyle;

import com.wskim.woowahan.excel.style.align.ExcelAlign;
import com.wskim.woowahan.excel.style.align.NoExcelAlign;
import com.wskim.woowahan.excel.style.border.ExcelBorders;
import com.wskim.woowahan.excel.style.border.NoExcelBorders;
import com.wskim.woowahan.excel.style.color.DefaultExcelColor;
import com.wskim.woowahan.excel.style.color.ExcelColor;
import com.wskim.woowahan.excel.style.color.NoExcelColor;

public class ExcelCellStyleConfigurer {

    ExcelAlign align = new NoExcelAlign();
    ExcelBorders border = new NoExcelBorders();
    ExcelColor foregroundColor = new NoExcelColor();

    public ExcelCellStyleConfigurer excelAlign(ExcelAlign excelAlign){
        this.align = excelAlign;
        return this;
    }

    public ExcelCellStyleConfigurer excelBorders(ExcelBorders excelBorders){
        this.border = excelBorders;
        return this;
    }

    public ExcelCellStyleConfigurer foreGroundColor(int red, int green, int blue){
        this.foregroundColor = DefaultExcelColor.rgb(red, blue, green);
        return this;
    }

    public void configurer(CellStyle cellStyle){
        align.apply(cellStyle);
        border.apply(cellStyle);
        foregroundColor.applyForeground(cellStyle);
    }
}
