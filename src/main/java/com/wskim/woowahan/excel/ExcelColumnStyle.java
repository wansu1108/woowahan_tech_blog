package com.wskim.woowahan.excel;

import com.wskim.woowahan.excel.style.ExcelCellStyle;

public @interface ExcelColumnStyle {
    
    /**
	 * Enum implements {@link com.wskim.woowahan.excel.style.ExcelCellStyle}
	 * Also, can use just class.
	 * If not use Enum, enumName will be ignored
	 * @see com.lannstark.style.DefaultExcelCellStyle
	 * @see com.lannstark.style.CustomExcelCellStyle
	 */
	Class<? extends ExcelCellStyle> excelCellStyleClass();

	/**
	 * name of Enum implements {@link com.lannstark.style.ExcelCellStyle}
	 * if not use Enum, enumName will be ignored
	 */
	String enumName() default "";
}
