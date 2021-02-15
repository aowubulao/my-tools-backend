package com.neoniou.tools.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author neo.zzj
 */
@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * NeoException 异常处理
     *
     * @param e NeoException
     * @return ResponseEntity
     */
    @ExceptionHandler(NeoException.class)
    public ResponseEntity<NeoResult> handleException(NeoException e) {
        return ResponseEntity.status(e.getNeoResult().getStatus()).body(e.getNeoResult());
    }

    /**
     * 全局异常处理，返回 ExceptionResult
     *
     * @param e Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<NeoResult> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new NeoResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}