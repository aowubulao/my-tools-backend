package com.neoniou.tools.filter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.neoniou.tools.exception.NeoException;
import com.neoniou.tools.exception.NeoResult;
import com.neoniou.tools.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Neo.Zzj
 * @date 2021/3/23
 */
@Slf4j
@Component
public class IpFilter implements HandlerInterceptor {

    private static final int MAP_SIZE = 1024;
    private static final int MAX_GET = 100;
    private static final long BLACK_LIST_TIME = 10 * 60L;

    private static Map<String, Integer> countMap = new ConcurrentHashMap<>(MAP_SIZE);

    private static final TimedCache<String, Integer> BLACK_LIST = CacheUtil.newTimedCache(BLACK_LIST_TIME);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ipAddr = IpUtil.getIpAddr(request);
        if (isBlackList(ipAddr) || !judgeIpAddress(ipAddr)) {
            throw new NeoException(new NeoResult(HttpStatus.OK.value(), -1, "error", ""));
        }
        return true;
    }

    //@Scheduled(cron = "0 */1 * * * ?")
    public void refreshMap() {
        System.out.println(1);
        countMap = new ConcurrentHashMap<>(MAP_SIZE);
    }

    private boolean isBlackList(String ipAddr) {
        if (BLACK_LIST.containsKey(ipAddr)) {
            BLACK_LIST.put(ipAddr, 0);
            return true;
        } else {
            return false;
        }
    }

    private boolean judgeIpAddress(String ipAddr) {
        if (!countMap.containsKey(ipAddr)) {
            countMap.put(ipAddr, 0);
        }
        Integer count = countMap.get(ipAddr);
        countMap.put(ipAddr, ++count);

        if (count > MAX_GET) {
            log.info("ip:[{}] is in black list now", ipAddr);
            BLACK_LIST.put(ipAddr, 0);
            return false;
        } else {
            return true;
        }
    }
}
