package com.wskim.woowahan.excel.resource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.wskim.woowahan.common.util.SuperClassReflectionUtils;
import com.wskim.woowahan.excel.DefaultBodyStyle;
import com.wskim.woowahan.excel.DefaultHeaderStyle;
import com.wskim.woowahan.excel.ExcelColumn;
import com.wskim.woowahan.excel.ExcelColumnStyle;
import com.wskim.woowahan.excel.exception.InvalidExcelCellStyleException;
import com.wskim.woowahan.excel.resource.collection.PreCalculatedCellStyleMap;
import com.wskim.woowahan.excel.style.ExcelCellStyle;
import com.wskim.woowahan.excel.style.NoExcelCellStyle;

public class ExcelRenderResourceFactory {

    public static ExcelRenderResource prepareRenderResource(Class<?> type, Workbook wb) {

        PreCalculatedCellStyleMap styleMap = new PreCalculatedCellStyleMap();
        Map<String, String> headerNamesMap = new LinkedHashMap<>();
		List<String> fieldNames = new ArrayList<>();

        ExcelColumnStyle classDefinedHeaderStyle = getHeaderExcelColumnStyle(type); // Default HeaderExcelColumnStyle
		ExcelColumnStyle classDefinedBodyStyle = getBodyExcelColumnStyle(type);     // Default BodyExcelColumnStyle

        for(Field field : SuperClassReflectionUtils.getAllFields(type)){
            if(field.isAnnotationPresent(ExcelColumn.class)){
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                
                styleMap.put(
                    ExcelCellKey.of(field.getName(), ExcelRenderLocation.HEADER)
                    , getCellStyle(decideAppliedStyleAnnotation(classDefinedHeaderStyle, annotation.headerStyle())), wb);

                    styleMap.put(
                        ExcelCellKey.of(field.getName(), ExcelRenderLocation.BODY)
                        , getCellStyle(decideAppliedStyleAnnotation(classDefinedBodyStyle, annotation.bodyStyle())), wb);    

                headerNamesMap.put(field.getName(), annotation.headerName());
                fieldNames.add(field.getName());
            }
        }

        return new ExcelRenderResource(headerNamesMap, fieldNames, styleMap);
    } 

    private static ExcelColumnStyle decideAppliedStyleAnnotation(ExcelColumnStyle classAnnotation, ExcelColumnStyle fieldAnnotation) {
        // ExcelColumStyle 지정
        if (fieldAnnotation.excelCellStyleClass().equals(NoExcelCellStyle.class) && classAnnotation != null) {
			return classAnnotation;
		}

		return fieldAnnotation;
    }

    @SuppressWarnings("deprecation")
    private static ExcelCellStyle getCellStyle(ExcelColumnStyle excelColumnStyle) {
        // ExcelColumnStyle에 지정된 내부 클래스
        Class<? extends ExcelCellStyle> excelCellStyleClass = excelColumnStyle.excelCellStyleClass();

        // 1. Case of Enum
        if(excelCellStyleClass.isEnum()){
            String enumName = excelColumnStyle.enumName();
            return findExcelCellStyle(excelCellStyleClass, enumName);
        }

        // 2. Case of Class
        try {
			return excelCellStyleClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new InvalidExcelCellStyleException(e.getMessage(), e);
		}
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static ExcelCellStyle findExcelCellStyle(Class<? extends ExcelCellStyle> excelCellStyles,String enumName) {
        try {
			return (ExcelCellStyle) Enum.valueOf((Class<Enum>) excelCellStyles, enumName);
		} catch (NullPointerException e) {
			throw new InvalidExcelCellStyleException("enumName must not be null", e);
		} catch (IllegalArgumentException e) {
			throw new InvalidExcelCellStyleException(String.format("Enum %s does not name %s", excelCellStyles.getName(), enumName), e);
		}

    }

    private static ExcelColumnStyle getHeaderExcelColumnStyle(Class<?> clazz) {
        Annotation annotation = SuperClassReflectionUtils.getAnnotation(clazz, DefaultHeaderStyle.class);
        if(annotation == null){
            return null;
        }
        return ((DefaultHeaderStyle) annotation).style();
	}

	private static ExcelColumnStyle getBodyExcelColumnStyle(Class<?> clazz) {
        Annotation annotation = SuperClassReflectionUtils.getAnnotation(clazz, DefaultBodyStyle.class);
        if(annotation == null){
            return null;
        }
        return ((DefaultHeaderStyle) annotation).style();
	}
}
