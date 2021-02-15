package com.neoniou.tools.service;

/**
 * @author Neo.Zzj
 * @date 2020/12/22
 */
public interface ShortLinkService {

    /**
     * 创建短链接
     * @param sourceUrl 源链接
     * @return 短链接
     */
    String createShortLink(String sourceUrl);

    /**
     * 根据短链接获取原链接
     * @param shortKey 短链接
     * @return 原链接
     */
    String getShortLink(String shortKey);
}
