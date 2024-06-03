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
public class BreadDto {
    
    @ExcelColumn(
        headerName = "브랜드"
        , headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "GREY_HEADER")
        , bodyStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BODY")
    )
    String brand;
    @ExcelColumn(
        headerName = "상품명"
        , headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "GREY_HEADER")
        , bodyStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BODY")
    )
    String productName;
    @ExcelColumn(
        headerName = "가격"
        , headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "RED_HEADER")
        , bodyStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BODY")
    )
    int price;
    @ExcelColumn(
        headerName = "평점"
        , headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "RED_HEADER")
        , bodyStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BODY")
    )
    double rating;
}
