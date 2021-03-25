package com.neoniou.tools.mvc.service;

import com.neoniou.tools.mvc.controller.pojo.IllustGetConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author Neo.Zzj
 * @date 2021/3/19
 */
public interface PixivService {

    /**
     * 获取插画信息
     *
     * @param id 插画pid
     * @param illustGetConfig 获取配置
     * @return 插画data
     */
    String getIllustInfo(String id, IllustGetConfig illustGetConfig);

    /**
     * 代理Pixiv图片
     *
     * @param request Rqt
     * @param response Res
     * @return 图片流
     */
    InputStream proxyPixivImage(HttpServletRequest request, HttpServletResponse response);
}
