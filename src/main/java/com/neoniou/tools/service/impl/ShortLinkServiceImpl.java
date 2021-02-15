package com.neoniou.tools.service.impl;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.neoniou.tools.exception.NeoException;
import com.neoniou.tools.exception.NeoResult;
import com.neoniou.tools.pojo.ShortLink;
import com.neoniou.tools.service.ShortLinkService;
import com.neoniou.tools.utils.ThreadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Neo.Zzj
 * @date 2020/12/22
 */
@Service
public class ShortLinkServiceImpl implements ShortLinkService {

    private static final String URL_REGEX = "[a-zA-z]+://[^\\s]*";

    private static final String FILE = System.getProperty("user.dir") + "/data/shortLink.json";

    private static final String BASE_STRING = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM";

    private static final String BASE_DOMAIN = "neoniou.com/s/";

    private static final Map<String, String> SOURCE_TO_KEY_MAP = new ConcurrentHashMap<>();

    private static final Map<String, String> KEY_TO_SOURCE_MAP = new ConcurrentHashMap<>();

    private static List<ShortLink> shortLinks = new CopyOnWriteArrayList<>();

    /**
     * 长链接最长500
     */
    private static final int MAX_LENGTH = 500;

    static {
        read();
    }

    @Override
    public String createShortLink(String sourceUrl) {
        if (!sourceUrl.matches(URL_REGEX)) {
            throw new NeoException(new NeoResult(HttpStatus.BAD_REQUEST.value(), "网址不合法！"));
        }
        checkRequest(sourceUrl);
        return add(sourceUrl);
    }

    @Override
    public String getShortLink(String shortKey) {
        if (!KEY_TO_SOURCE_MAP.containsKey(shortKey)) {
            throw new NeoException(new NeoResult(HttpStatus.NOT_FOUND.value(), "not found!"));
        }
        return KEY_TO_SOURCE_MAP.get(shortKey);
    }

    private void checkRequest(String sourceUrl) {
        if (sourceUrl.length() > MAX_LENGTH) {
            throw new NeoException(new NeoResult(HttpStatus.BAD_REQUEST.value(), "链接过长！"));
        }
    }

    private static void read() {
        shortLinks = JSONUtil.toList(new FileReader(FILE).readString(), ShortLink.class);
        for (ShortLink shortLink : shortLinks) {
            SOURCE_TO_KEY_MAP.put(shortLink.getSourceUrl(), shortLink.getShortKey());
            KEY_TO_SOURCE_MAP.put(shortLink.getShortKey(), shortLink.getSourceUrl());
        }
    }

    /**
     * 添加一个长链接
     *
     * @param sourceUrl 原链接
     * @return 生成的短链接
     */
    private static String add(String sourceUrl) {
        if (SOURCE_TO_KEY_MAP.containsKey(sourceUrl)) {
            return BASE_DOMAIN + SOURCE_TO_KEY_MAP.get(sourceUrl);
        }
        String shortKey = RandomUtil.randomString(BASE_STRING, 6);
        while (KEY_TO_SOURCE_MAP.containsKey(shortKey)) {
            shortKey = RandomUtil.randomString(BASE_STRING, 6);
        }
        SOURCE_TO_KEY_MAP.put(sourceUrl, shortKey);
        KEY_TO_SOURCE_MAP.put(shortKey, sourceUrl);

        String finalShortKey = shortKey;
        ThreadUtil.execute(() -> {
            shortLinks.add(new ShortLink(sourceUrl, finalShortKey));
            write();
        });

        return BASE_DOMAIN + shortKey;
    }

    private static void write() {
        JSONArray jsonArray = JSONUtil.parseArray(shortLinks);
        new FileWriter(FILE).write(jsonArray.toString());
    }
}