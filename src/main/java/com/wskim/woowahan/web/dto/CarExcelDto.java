package com.wskim.woowahan.web.dto;

import com.wskim.woowahan.excel.ExcelColumn;
import com.wskim.woowahan.excel.ExcelColumnStyle;
import com.wskim.woowahan.excel.style.DefaultExcelCellStyle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarExcelDto{
    @ExcelColumn(headerName = "회사"
    , headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class , enumName = "GREY_HEADER")
    , bodyStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class , enumName = "BODY"))
    private String company;
    @ExcelColumn(headerName = "차종"
    , headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class , enumName = "GREY_HEADER")
    , bodyStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class , enumName = "BODY"))
    private String name;
    @ExcelColumn(headerName = "가격"
    , headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class , enumName = "BLUE_HEADER")
    , bodyStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class , enumName = "BODY"))
    private int price;
    @ExcelColumn(headerName = "평점"
    , headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class , enumName = "BLUE_HEADER")
    , bodyStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class , enumName = "BODY"))
    private double rating;
}
