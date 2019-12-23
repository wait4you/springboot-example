package com.iyou.quartz.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.SchedulingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 *     全局异常处理
 * </p>
 * @author zhang.hp
 * @version 1.0
 * @description
 * @createDate 2019-11-23 06:29
 * @modifyDate 2019-11-23 06:29
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity exceptionHandler(Exception e) {
        log.error("全局异常处理", e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("全局异常处理 " + e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity schedulingExceptionHandler(SchedulingException e) {
        log.error("定时任务调度异常", e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("定时任务调度异常  " + e.getMessage());
    }
}