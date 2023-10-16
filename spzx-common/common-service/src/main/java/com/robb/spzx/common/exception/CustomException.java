package com.robb.spzx.common.exception;

import com.robb.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class CustomException extends RuntimeException{

    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;

    public CustomException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }





}
