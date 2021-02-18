package com.neoniou.tools.service;

import com.neoniou.tools.controller.pojo.DanMuFilter;
import com.neoniou.tools.pojo.DanMuInfo;
import com.neoniou.tools.pojo.PageList;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Neo.Zzj
 * @date 2021/2/14
 */
public interface BiliService {

    /**
     * 根据 BV号或 AV号获取封面链接
     *
     * @param code BV or AV code
     * @return cover image url
     */
    String getCover(String code);

    /**
     * 根据 BV SS EP 获取 cid以及分p情况
     *
     * @param code BV SS EP
     * @return PageList or Null
     */
    List<PageList> getPageList(String code);

    /**
     * 根据cid获取弹幕
     *
     * @param filter 视频cid以及过滤器
     * @return 弹幕列表 or null
     */
    List<DanMuInfo> getDanMu(DanMuFilter filter);
}
