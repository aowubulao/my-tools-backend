package com.neoniou.tools.exception;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.util.Date;

/**
 * @author neo.zzj
 */
@Data
public class NeoResult {

    private int status;

    private String message;

    private String timestamp;

    private String data;

    private int code;

    public NeoResult(int status, int code, String message, String data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = new DateTime(new Date()).toString();
        this.data = data;
    }

    public NeoResult(int status, String message) {
        this(status, 1,  message, null);
    }

    public NeoResult(String message, int code) {
        this(200, code, message, null);
    }

    public NeoResult(String data) {
        this(200, 1, "ok", data);
    }


}