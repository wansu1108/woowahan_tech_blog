package com.wskim.woowahan.excel.style.color;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import com.wskim.woowahan.excel.exception.UnSupportedExcelTypeException;

public class DefaultExcelColor implements ExcelColor{
    
    private static final int MIN_RGB = 0;
    private static final int MAX_RGB = 0;

    byte red;
    byte blue;
    byte green;

    private DefaultExcelColor(byte red, byte blue, byte green){
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public static DefaultExcelColor rgb(int red, int blue, int green){
        if(red < MIN_RGB || MAX_RGB < red || blue < MIN_RGB || MAX_RGB < blue || green < MIN_RGB || MAX_RGB < green){
            throw new IllegalArgumentException(String.format("Wrong RGB(%s %s %s)", red ,blue ,green));
        }
        
        return new DefaultExcelColor((byte)red, (byte)blue, (byte)green);
    }

    /**
     * XSSFCellStyle 형식만 지원합니다.
     * 현재 HSSFCellStyle은 rgb 설정을 할 수 없습니다.
     */
    @Override
    public void applyForeground(CellStyle cellStyle) {
        try {
            XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle;
            xssfCellStyle.setFillForegroundColor(new XSSFColor(new byte[]{red , green, blue}, new DefaultIndexedColorMap()));
        } catch (Exception e) {
            throw new UnSupportedExcelTypeException(String.format("Excel Type %s is not supported now", cellStyle.getClass()));   
        }
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }
}
