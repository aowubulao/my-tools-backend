package com.neoniou.tools.mvc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Neo.Zzj
 * @date 2020/12/22
 */
@Data
public class ShortLink implements Serializable {

    private String sourceUrl;

    private String shortKey;

    public ShortLink() {
    }

    public ShortLink(String sourceUrl, String shortKey) {
        this.sourceUrl = sourceUrl;
        this.shortKey = shortKey;
    }
}
