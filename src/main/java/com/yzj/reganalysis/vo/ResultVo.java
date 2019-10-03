package com.yzj.reganalysis.vo;

import lombok.Data;

import java.beans.IntrospectionException;

@Data
public class ResultVo<T> {
    private Integer status;
    private String message;
    private T data;
}
