package com.wskim.woowahan.excel;

// Excel과 관련된 에러를 처리하기 위한 공통 Exception
// Excel 모듈에서 발생하는 전체 에러를 관리하기 위한 방식처럼 보여짐
public class ExcelException extends RuntimeException{
    public ExcelException(String message, Throwable throwable){
        super(message, throwable);
    }
}
