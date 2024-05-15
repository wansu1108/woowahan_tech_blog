package com.wskim.woowahan.excel;

import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.wskim.woowahan.excel.dto.CarExcelDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Oct.08.2020 최태현" 님이 작성해주신 글을 참고하였습니다.
 * https://techblog.woowahan.com/2698/
 */
@RestController
@AllArgsConstructor
@Slf4j
public class ExcelDownload {

    private final CarExcelRepository repository;
    
    @GetMapping("/api/v1/excel/car")
    public void downloadCarInfoV1(HttpServletResponse response) throws IOException {
        Workbook workbook = new HSSFWorkbook(); // SXSSF구현체는 의존성이 너무많이 필요해서,, HSS로 할게용
        CellStyle greyCellStyle = workbook.createCellStyle();
        applyCellStyle(greyCellStyle, HSSFColor.GREY_25_PERCENT.index);
        CellStyle blueCellStyle = workbook.createCellStyle();
        applyCellStyle(blueCellStyle, HSSFColor.BLUE_GREY.index);
        CellStyle bodyCellStyle = workbook.createCellStyle();
        applyCellStyle(bodyCellStyle, HSSFColor.WHITE.index);

        CellStyle bodyNumberStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        applyCellStyle(bodyNumberStyle, HSSFColor.WHITE.index);
        bodyNumberStyle.setDataFormat(dataFormat.getFormat("#,##0"));

        CellStyle bodyDoubleStyle = workbook.createCellStyle();
        DataFormat dataFormat2 = workbook.createDataFormat();
        applyCellStyle(bodyDoubleStyle, HSSFColor.WHITE.index);
        bodyNumberStyle.setDataFormat(dataFormat2.getFormat("#,##0.00"));
    
        Sheet sheet = workbook.createSheet();

        List<CarExcelDto> carExcelDtos = repository.findAll();

        int rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex++);
        
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellStyle(greyCellStyle);
        headerCell1.setCellValue("회사");

        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellStyle(greyCellStyle);
        headerCell2.setCellValue("차종");

        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellStyle(blueCellStyle);
        headerCell3.setCellValue("가격");

        Cell headerCell4 = headerRow.createCell(3);
        headerCell4.setCellStyle(blueCellStyle);
        headerCell4.setCellValue("평점");

        for(CarExcelDto dto : carExcelDtos){
            Row bodyRow = sheet.createRow(rowIndex++);
            
            Cell bodyCell1 = bodyRow.createCell(0);
            bodyCell1.setCellStyle(bodyCellStyle);
            bodyCell1.setCellValue(dto.getCompany());

            Cell bodyCell2 = bodyRow.createCell(1);
            bodyCell2.setCellStyle(bodyCellStyle);
            bodyCell2.setCellValue(dto.getName());

            Cell bodyCell3 = bodyRow.createCell(2);
            bodyCell3.setCellStyle(bodyNumberStyle);
            bodyCell3.setCellValue(dto.getPrice());

            Cell bodyCell4 = bodyRow.createCell(3);
            bodyCell4.setCellStyle(bodyDoubleStyle);
            bodyCell4.setCellValue(dto.getRating());
        }

        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = String.format("%s.xlsx", formatedNow);

        response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");
        
        workbook.write(response.getOutputStream());
        // workbook).close();
    }

    public void applyCellStyle(CellStyle cellStyle, short color){
        HSSFCellStyle hssfCellStyle = (HSSFCellStyle) cellStyle;        
        hssfCellStyle.setFillForegroundColor(color);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
    }

    // Data
    @PostConstruct
    public void init(){
        repository.initTable();
        repository.save(new CarExcelDto("현대", "모닝", 1000, 4.9));
        repository.save(new CarExcelDto("르노삼성", "QM6", 2000000, 4.8));
        repository.save(new CarExcelDto("기아", "K7", 300000, 4.7));
        List<CarExcelDto> lists = repository.findAll();
        log.info("carInfo : {}", lists.toString());
    }
}
