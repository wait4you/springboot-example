package com.iyou.quartz.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;

/**
 * @author zhang.hp
 * @version 1.0
 * @description 服务响应
 * @createDate 2019-08-10 08:49
 * @modifyDate 2019-08-10 08:49
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Response<T> {
    private String status;
    private String message;
    private T metadata;

    public static Response success(String... message) {
        return new Response()
                .setStatus(ResponseCode.OK.getCode())
                .setMessage(Arrays.toString(message));
    }

    public static <T> Response success(T metadata, String... message) {
        return new Response<>()
                .setStatus(ResponseCode.OK.getCode())
                .setMessage(Arrays.toString(message))
                .setMetadata(metadata);
    }

    public static Response error(String... message) {
        return new Response()
                .setStatus(ResponseCode.ERR.getCode())
                .setMessage(Arrays.toString(message));
    }

    public static Response error(String code, String message) {
        return new Response()
                .setStatus(code)
                .setMessage(message);
    }


    @JsonIgnore // 不再序列化结果当中
    public boolean isSuccess() {
        return ResponseCode.OK.getCode().equals(this.status);
    }

    @JsonIgnore
    public boolean isNotSuccess() {
        return !isSuccess();
    }
}
