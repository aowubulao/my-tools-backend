package com.neoniou.tools.mvc.model;

import lombok.Data;

/**
 * @author Neo.Zzj
 * @date 2021/2/15
 */
@Data
public class PageList {

    private String title;

    private String cid;

    public PageList(String title, String cid) {
        this.title = title;
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
