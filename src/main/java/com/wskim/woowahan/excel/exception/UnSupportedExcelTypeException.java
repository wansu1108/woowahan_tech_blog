package com.wskim.woowahan.excel.exception;

import com.wskim.woowahan.excel.ExcelException;

public class UnSupportedExcelTypeException extends ExcelException{

    public UnSupportedExcelTypeException(String message) {
        // throwable을 null을 넘기는 경우 : 실제 예외가 아닌, 로직상 예외를 던져야 하는 경우로 보여짐
        super(message, null);
    }
}
