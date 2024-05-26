package com.wskim.woowahan.excel.resource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.wskim.woowahan.common.util.SuperClassReflectionUtils;
import com.wskim.woowahan.excel.ExcelColumn;

public class ExcelRenderResourceFactory {

    public static ExcelRenderResource prepareRenderResource(Class<?> type, Workbook wb) {

        Map<String, String> headerNamesMap = new LinkedHashMap<>();
		List<String> fieldNames = new ArrayList<>();

        for(Field field : SuperClassReflectionUtils.getAllFields(type)){
            if(field.isAnnotationPresent(ExcelColumn.class)){
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                headerNamesMap.put(field.getName(), annotation.headerName());
                fieldNames.add(field.getName());
            }
        }

        return new ExcelRenderResource(headerNamesMap, fieldNames);
    }
}
