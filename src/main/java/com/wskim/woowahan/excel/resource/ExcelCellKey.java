package com.wskim.woowahan.excel.resource;

import java.util.Objects;

public class ExcelCellKey {
    
    private final String dataFieldName;
	private final ExcelRenderLocation excelRenderLocation;

    private ExcelCellKey(String dataFieldName, ExcelRenderLocation excelRenderLocation){
        this.dataFieldName=dataFieldName;
        this.excelRenderLocation=excelRenderLocation;
    }

    public static ExcelCellKey of(String dataFieldName, ExcelRenderLocation excelRenderLocation){
        assert excelRenderLocation != null; // if excelRenderLocation == null then throw new IllegalArgumentException()
        return new ExcelCellKey(dataFieldName, excelRenderLocation);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) { return true; }
        if (o == null || getClass() != o.getClass()) return false;
        ExcelCellKey that = (ExcelCellKey) o;
		return Objects.equals(dataFieldName, that.dataFieldName) && excelRenderLocation == that.excelRenderLocation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataFieldName, excelRenderLocation);
    }
}
