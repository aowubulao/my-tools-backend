package com.neoniou.tools.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.neoniou.tools.exception.NeoResult;
import com.neoniou.tools.pojo.DanMuInfo;
import com.neoniou.tools.pojo.PageList;
import com.neoniou.tools.service.BiliService;
import com.neoniou.tools.utils.BiliCrcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Neo.Zzj
 * @date 2021/2/14
 */
@RestController
@RequestMapping("/tools/bili")
public class BiliController {

    @Autowired
    private BiliService biliService;

    @GetMapping("/getCover")
    public ResponseEntity<NeoResult> getCover(@RequestParam("code") String code) {
        String coverUrl = biliService.getCover(code);
        if (coverUrl == null) {
            return ResponseEntity.ok(new NeoResult("发生错误！", -1));
        } else {
            return ResponseEntity.ok(new NeoResult(coverUrl));
        }
    }

    @GetMapping("/getPageList")
    public ResponseEntity<NeoResult> getPageList(@RequestParam("code") String code) {
        List<PageList> pageLists = biliService.getPageList(code);
        if (pageLists == null) {
            return ResponseEntity.ok(new NeoResult("发生错误！", -1));
        } else {
            JSONArray result = JSONUtil.parseArray(pageLists);
            return ResponseEntity.ok(new NeoResult(result.toString()));
        }
    }

    @GetMapping("/getDanMu")
    public ResponseEntity<NeoResult> getDanMu(@RequestParam("cid") String cid) {
        List<DanMuInfo> danMuInfos = biliService.getDanMu(cid);
        if (danMuInfos == null) {
            return ResponseEntity.ok(new NeoResult("该视频没有弹幕或是cid错误！", -1));
        } else {
            JSONArray result = JSONUtil.parseArray(danMuInfos);
            return ResponseEntity.ok(new NeoResult(result.toString()));
        }
    }

    @GetMapping("/crcReverse")
    public ResponseEntity<NeoResult> getCrcReverse(@RequestParam("crc") String crc) {
        String result = BiliCrcUtil.calculateOrigin(crc);
        if (result == null) {
            return ResponseEntity.ok(new NeoResult("Crc代码出错！", -1));
        } else {
            return ResponseEntity.ok(new NeoResult(result));
        }
    }
}
