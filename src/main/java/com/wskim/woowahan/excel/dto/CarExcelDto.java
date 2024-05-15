package com.wskim.woowahan.excel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarExcelDto {
    private String company;
    private String name;
    private int price;
    private double rating;
}
