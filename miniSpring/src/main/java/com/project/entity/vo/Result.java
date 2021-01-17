package com.project.entity.vo;

import lombok.Data;

@Data
public class Result<T> {
    // 返回状态码
    private int code;
    // 返回状态信息
    private String msg;
    // 返回数据
    private T data;
}
