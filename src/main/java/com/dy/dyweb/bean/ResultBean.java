package com.dy.dyweb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultBean<T> {

    public static final int PHONE_EXIST = 1001;

    private int code;

    private T data;

    private String msg;

    public static ResultBean ok(Object data) {
        return new ResultBean(200, data, "请求成功！");
    }

    public static ResultBean ok(String msg) {
        return new ResultBean(200, null, msg);
    }

    public static ResultBean ok(int code, String msg) {
        return new ResultBean(code, null, msg);
    }

    public static ResultBean ok(Object data, String msg) {
        return new ResultBean(200, data, msg);
    }

    public static ResultBean fail(String msg) {
        return new ResultBean(400, null,  msg);
    }


}
