package com.iyou.quartz.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhang.hp
 * @version 1.0
 * @description 响应码
 * @createDate 2019-08-10 08:57
 * @modifyDate 2019-08-10 08:57
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseCode {
    OK("0", "OK"),
    ERR("1", "ERROR"),
    ILLEGAL_ARGUMENT("2", "ILLEGAL_ARGUMENT")
    ;

    private final String code;
    private final String description;
}
