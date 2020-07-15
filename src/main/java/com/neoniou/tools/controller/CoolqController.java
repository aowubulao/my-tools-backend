package com.neoniou.tools.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Neo.Zzj
 * @date 2020/7/15
 */
@RestController
@RequestMapping("/coolqApi")
public class CoolqController {

    @PostMapping("/sendMessage")
    public ResponseEntity<Void> getCoolqMessage(String message) {
        System.out.println(message);
        /*Map<String, String[]> map = request.getParameterMap();
        System.out.println(map.size());
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + Arrays.toString(entry.getValue()));
        }*/
        return ResponseEntity.ok().build();
    }
}
