package com.neoniou.tools.exception;

import lombok.Getter;

/**
 * @author neo.zzj
 */
@Getter
public class NeoException extends RuntimeException{

    private NeoResult neoResult;

    public NeoException() {
    }

    public NeoException(NeoResult neoResult) {
        this.neoResult = neoResult;
    }
}