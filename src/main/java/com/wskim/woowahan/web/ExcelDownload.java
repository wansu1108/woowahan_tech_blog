package com.wskim.woowahan.web;

import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.wskim.woowahan.excel.SimpleExcelFile;
import com.wskim.woowahan.web.dto.BreadDto;
import com.wskim.woowahan.web.dto.CarExcelDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Oct.08.2020 최태현" 님이 작성해주신 글을 참고하였습니다.
 * https://techblog.woowahan.com/2698/
 */
@RestController
@AllArgsConstructor
public class ExcelDownload {

    private final CarExcelRepository repository;
    private final BreadExcelRepository breadExcelRepository;
    
    @GetMapping("/api/v1/excel/car")
    public void downloadCarInfoV1(HttpServletResponse response) throws IOException {
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        CellStyle greyCellStyle = workbook.createCellStyle();
        applyCellStyle(greyCellStyle, new Color(231, 234, 236));

        CellStyle blueCellStyle = workbook.createCellStyle();
        applyCellStyle(blueCellStyle, new Color(223, 235, 246));

        CellStyle bodyCellStyle = workbook.createCellStyle();
        applyCellStyle(bodyCellStyle, new Color(255, 255, 255));

        XSSFDataFormat dataFormat = (XSSFDataFormat) workbook.createDataFormat();
        
        // 통화 타입
        CellStyle integerCellStyle = workbook.createCellStyle();
        applyCellStyle(integerCellStyle, new Color(255, 255, 255));
        integerCellStyle.setDataFormat(dataFormat.getFormat("#,##0"));

        // 소수점 타입s
        CellStyle doubleCellStyle = workbook.createCellStyle();
        applyCellStyle(doubleCellStyle, new Color(255, 255, 255));
        doubleCellStyle.setDataFormat(dataFormat.getFormat("#,##0.00"));

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
            bodyCell3.setCellStyle(integerCellStyle);
            bodyCell3.setCellValue(dto.getPrice());

            Cell bodyCell4 = bodyRow.createCell(3);
            bodyCell4.setCellStyle(doubleCellStyle);
            bodyCell4.setCellValue(dto.getRating());
        }

        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = String.format("%s.xlsx", formatedNow);

        response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");
        
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void applyCellStyle(CellStyle cellStyle, Color color){
        XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle;
        xssfCellStyle.setFillForegroundColor(new XSSFColor(color, new DefaultIndexedColorMap()));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
    }

    @GetMapping("/api/v2/excel/car")
    public void downloadCarInfoV2(HttpServletResponse response) throws IOException {
        List<CarExcelDto> data = repository.findAll();
        SimpleExcelFile<CarExcelDto> excelFile = new SimpleExcelFile<>(data, CarExcelDto.class);

        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = String.format("%s.xlsx", formatedNow);

        response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");
        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/api/v2/excel/bread")
    public void downaloadBreadInfoV2(HttpServletResponse response) throws IOException {
        List<BreadDto> data = breadExcelRepository.findAll();
        SimpleExcelFile<BreadDto> excelFile = new SimpleExcelFile<>(data, BreadDto.class);

        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = String.format("%s.xlsx", formatedNow);

        response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");
        excelFile.write(response.getOutputStream());
    }
    

    @PostConstruct
    public void init(){
        repository.initTable();
        repository.save(new CarExcelDto("KIA", "모닝",1000,4.9));
        repository.save(new CarExcelDto("HEYNDAI", "소나타",3000,4.7));
        repository.save(new CarExcelDto("르노삼성", "QM6",5000,4.6));

        breadExcelRepository.initTable();
        breadExcelRepository.save(new BreadDto("파리바게트","퀸아망",3100,3.5));
        breadExcelRepository.save(new BreadDto("뚜레쥬르","호두연유바게트",3800,3.2));
        breadExcelRepository.save(new BreadDto("레제드라마","크레이프",7500,4.5));
        breadExcelRepository.save(new BreadDto("브라운슈가","마틸다케익",9000,4.7));
        breadExcelRepository.save(new BreadDto("호암제과","피스타치오딸기베이글",6300,5.0));
    }
}
