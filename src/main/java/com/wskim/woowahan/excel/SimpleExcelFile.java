package com.wskim.woowahan.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.wskim.woowahan.common.util.SuperClassReflectionUtils;
import com.wskim.woowahan.excel.resource.ExcelRenderLocation;
import com.wskim.woowahan.excel.resource.ExcelRenderResource;
import com.wskim.woowahan.excel.resource.ExcelRenderResourceFactory;

public class SimpleExcelFile<T> {
    private static final SpreadsheetVersion supplyExcelVersion = SpreadsheetVersion.EXCEL2007;
    private static final int ROW_START_INDEX = 0;
    private static final int COLUMN_START_INDEX = 0;

    private SXSSFWorkbook wb;
    private Sheet sheet;
    private ExcelRenderResource excelRenderResource;

    public SimpleExcelFile(List<?> data, Class<T> type){
        validateMaxRow(data);
        this.wb = new SXSSFWorkbook();
        this.excelRenderResource = ExcelRenderResourceFactory.prepareRenderResource(type, wb);
        renderExcel(data);
    }

    
    // SXSSFWorkbook maxRow수를 초과하는지 판단
    private void validateMaxRow(List<?> data){
        int maxRow = supplyExcelVersion.getMaxRows();
        if(data.size() >= maxRow){
            throw new IllegalArgumentException(
                String.format("this concreate Excelfile does not supports over %s rows", maxRow));
            }
        }

    
    private void renderExcel(List<?> data) {
        sheet = wb.createSheet();
        renderHeaders(sheet, ROW_START_INDEX, COLUMN_START_INDEX);

        if(data.size() == 0){
            return;
        }

        int rowIndex = ROW_START_INDEX + 1;
        for(Object renderData : data){
            renderBody(renderData, rowIndex++, COLUMN_START_INDEX);
        }
    }

    private void renderHeaders(Sheet sheet, int rowIndex, int columnStartIndex) {
        Row row = sheet.createRow(rowIndex);

        int columnIndex = columnStartIndex;
        for(String fieldName : excelRenderResource.getAllDataFieldNames()){
            Cell cell = row.createCell(columnIndex++);
            cell.setCellValue(excelRenderResource.getExcelHeaderName(fieldName));
            cell.setCellStyle(excelRenderResource.getCellStyle(fieldName, ExcelRenderLocation.HEADER));
        }
    }
    
    private void renderBody(Object data, int rowIndex, int columnStartIndex) {
        Row row = sheet.createRow(rowIndex);
        
        int columnIndex = columnStartIndex;
        for(String dataFieldName : excelRenderResource.getAllDataFieldNames()){
            Cell cell = row.createCell(columnIndex++);
            cell.setCellStyle(excelRenderResource.getCellStyle(dataFieldName, ExcelRenderLocation.BODY));
            try {
                Field field = SuperClassReflectionUtils.getField(data.getClass(), dataFieldName);
                field.setAccessible(true);
                Object cellValue = field.get(data);
                renderCellValue(cell, cellValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void renderCellValue(Cell cell, Object cellValue) {
        if(cellValue instanceof Number){
            Number number = (Number) cellValue;
            cell.setCellValue(number.doubleValue());
        }

        cell.setCellValue(cellValue == null ? "" : cellValue.toString());
    }


    public void write(OutputStream stream) throws IOException{
        wb.write(stream);
        wb.dispose();
        wb.close();
        stream.close();
    }
}
    
