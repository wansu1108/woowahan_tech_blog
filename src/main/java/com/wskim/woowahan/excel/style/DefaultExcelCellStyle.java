package com.wskim.woowahan.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;

import com.wskim.woowahan.excel.style.align.DefaultExcelAlign;
import com.wskim.woowahan.excel.style.align.ExcelAlign;
import com.wskim.woowahan.excel.style.border.DefaultExcelBorders;
import com.wskim.woowahan.excel.style.border.ExcelBorderStyle;
import com.wskim.woowahan.excel.style.color.DefaultExcelColor;
import com.wskim.woowahan.excel.style.color.ExcelColor;

public enum DefaultExcelCellStyle implements ExcelCellStyle{

    GREY_HEADER(DefaultExcelColor.rgb(217, 217, 217),
			DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER),
	BLUE_HEADER(DefaultExcelColor.rgb(223, 235, 246),
			DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER),
	BODY(DefaultExcelColor.rgb(255, 255, 255),
			DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.RIGHT_CENTER),

	RED_HEADER(DefaultExcelColor.rgb(255,0,0),
			DefaultExcelBorders.newInstance(ExcelBorderStyle.THICK), DefaultExcelAlign.CENTER_CENTER);

    private final ExcelColor backgroundColor;
    private final DefaultExcelBorders borders;
    private final ExcelAlign align;

    private DefaultExcelCellStyle(ExcelColor backgroundColor, DefaultExcelBorders borders, ExcelAlign align) {
		this.backgroundColor = backgroundColor;
		this.borders = borders;
		this.align = align;
	}

    @Override
    public void apply(CellStyle cellStyle) {
        backgroundColor.applyForeground(cellStyle);
		borders.apply(cellStyle);
		align.apply(cellStyle);
    }
}
