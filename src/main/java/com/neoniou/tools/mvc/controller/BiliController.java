package com.neoniou.tools.mvc.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.neoniou.tools.exception.NeoResult;
import com.neoniou.tools.mvc.controller.pojo.DanMuFilter;
import com.neoniou.tools.mvc.model.DanMuInfo;
import com.neoniou.tools.mvc.model.PageList;
import com.neoniou.tools.mvc.service.BiliService;
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
@RequestMapping("/tools/v1/bili")
public class BiliController {

    @Autowired
    private BiliService biliService;

    @GetMapping("/cover")
    public ResponseEntity<NeoResult> getCover(@RequestParam("code") String code) {
        String coverUrl = biliService.getCover(code);
        if (coverUrl == null) {
            return ResponseEntity.ok(new NeoResult("发生错误！", -1));
        } else {
            return ResponseEntity.ok(new NeoResult(coverUrl));
        }
    }

    @GetMapping("/video/page")
    public ResponseEntity<NeoResult> getPageList(@RequestParam("code") String code) {
        List<PageList> pageLists = biliService.getPageList(code);
        if (pageLists == null) {
            return ResponseEntity.ok(new NeoResult("发生错误！", -1));
        } else {
            JSONArray result = JSONUtil.parseArray(pageLists);
            return ResponseEntity.ok(new NeoResult(result.toString()));
        }
    }

    @GetMapping("/video/danmu")
    public ResponseEntity<NeoResult> getDanMu(DanMuFilter danMuFilter) {
        List<DanMuInfo> danMuInfos = biliService.getDanMu(danMuFilter);
        if (danMuInfos == null) {
            return ResponseEntity.ok(new NeoResult("该视频没有弹幕或是cid错误！", -1));
        } else {
            JSONArray result = JSONUtil.parseArray(danMuInfos);
            return ResponseEntity.ok(new NeoResult(result.toString()));
        }
    }

    @GetMapping("/crc_reverse")
    public ResponseEntity<NeoResult> getCrcReverse(@RequestParam("crc") String crc) {
        String result = BiliCrcUtil.calculateOrigin(crc);
        if (result == null) {
            return ResponseEntity.ok(new NeoResult("Crc代码出错！", -1));
        } else {
            return ResponseEntity.ok(new NeoResult(result));
        }
    }
}
