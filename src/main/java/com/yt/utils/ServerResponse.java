package com.yt.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 封装前端返回统一实体类
 * */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@NoArgsConstructor
public class ServerResponse<T> {
    private int suatus;    //0成功
    private T data;      //suatus=0，将返回的数据封装到data
    private String msg;  //提示信息

    private ServerResponse(int suatus) {
        this.suatus = suatus;
    }

    private ServerResponse(int suatus, T data) {
        this.suatus = suatus;
        this.data = data;
    }

    private ServerResponse(int suatus, T data, String msg) {
        this.suatus = suatus;
        this.data = data;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.suatus == 0;
    }

    public static ServerResponse creatServerResponseBySuccess() {
        return new ServerResponse(0);
    }

    public static <T> ServerResponse creatServerResponseBySuccess(T data) {
        return new ServerResponse(0, data);
    }

    public static <T> ServerResponse creatServerResponseBySuccess(T data, String msg) {
        return new ServerResponse(0, data, msg);
    }

    public static <T> ServerResponse creatServerResponseByFail(int suatus) {
        return new ServerResponse(suatus);
    }

    public static <T> ServerResponse creatServerResponseByFail(int suatus, String msg) {
        return new ServerResponse(suatus, null, msg);
    }
}
