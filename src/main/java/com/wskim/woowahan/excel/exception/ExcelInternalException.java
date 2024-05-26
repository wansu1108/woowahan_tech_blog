package com.wskim.woowahan.excel.exception;

import com.wskim.woowahan.excel.ExcelException;

public class ExcelInternalException extends ExcelException{

    public ExcelInternalException(String message, Throwable throwable) {
        // throwable 넘기는 경우 : 로직상 예외처리가 아닌, 실제 발생하는 예외처리
        super(message, throwable);
    }

}
