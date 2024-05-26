package com.wskim.woowahan.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;

import com.wskim.woowahan.excel.style.configurer.ExcelCellStyleConfigurer;

public abstract class CustomExcelCellStyle implements ExcelCellStyle{

    private ExcelCellStyleConfigurer configurer = new ExcelCellStyleConfigurer();

    public CustomExcelCellStyle(){
        configurer(configurer);
    }

    public abstract void configurer(ExcelCellStyleConfigurer configurer);

    @Override
    public void apply(CellStyle cellStyle) {
        configurer.configurer(cellStyle);
    }
}
