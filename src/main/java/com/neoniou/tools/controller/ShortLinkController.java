package com.neoniou.tools.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.neoniou.tools.exception.NeoException;
import com.neoniou.tools.exception.NeoResult;
import com.neoniou.tools.service.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Neo.Zzj
 * @date 2020/12/22
 */
@RestController
public class ShortLinkController {

    @Autowired
    private ShortLinkService shortLinkService;

    @PostMapping("/sc/create")
    public ResponseEntity<NeoResult> createShortLink(@RequestParam("sourceUrl") String sourceUrl) {
        String shortLink = shortLinkService.createShortLink(sourceUrl);
        return ResponseEntity.ok(new NeoResult(shortLink));
    }

    @GetMapping("/s/{shortKey}")
    public void getShortLink(HttpServletResponse response,
                                             @PathVariable("shortKey") String shortKey) throws IOException {
        String sourceUrl = shortLinkService.getShortLink(shortKey);
        response.sendRedirect(sourceUrl);
    }

    @GetMapping("/test")
    public void testM() {

    }
}
