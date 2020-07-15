package com.neoniou.tools.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Neo.Zzj
 * @date 2020/7/14
 */
@RestController
@RequestMapping("/myToolsApi/danmuFilter")
public class BiliController {

    @PostMapping("/login")
    public ResponseEntity<Void> loginBili() {
        return ResponseEntity.ok().build();
    }
}
