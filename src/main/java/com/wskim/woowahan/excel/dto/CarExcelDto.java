package com.wskim.woowahan.excel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarExcelDto{
    @ExcelColumn(headerName = "회사")
    private String company;
    @ExcelColumn(headerName = "차종")
    private String name;
    @ExcelColumn(headerName = "가격")
    private int price;
    @ExcelColumn(headerName = "평점")
    private double rating;
}
