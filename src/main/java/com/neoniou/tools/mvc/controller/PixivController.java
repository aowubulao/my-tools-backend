package com.neoniou.tools.mvc.controller;

import cn.hutool.core.io.IoUtil;
import com.neoniou.tools.exception.NeoResult;
import com.neoniou.tools.mvc.controller.pojo.IllustGetConfig;
import com.neoniou.tools.mvc.service.PixivService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Neo.Zzj
 * @date 2021/3/19
 */
@RestController
@RequestMapping("/pixiv/v1/")
public class PixivController {

    @Autowired
    private PixivService pixivService;

    @GetMapping("/illust/{id}")
    public ResponseEntity<NeoResult> getIllustInfo(@PathVariable("id") String id, IllustGetConfig illustGetConfig) {
        String illustInfo = pixivService.getIllustInfo(id, illustGetConfig);
        return ResponseEntity.ok().body(new NeoResult(illustInfo));
    }

    @GetMapping("/proxy/**")
    public void proxyPixivImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = pixivService.proxyPixivImage(request, response);
        IoUtil.copy(inputStream, response.getOutputStream());
        inputStream.close();
    }
}
