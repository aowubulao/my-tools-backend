package com.neoniou.tools.mvc.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.neoniou.tools.mvc.controller.pojo.IllustGetConfig;
import com.neoniou.tools.mvc.service.PixivService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author Neo.Zzj
 * @date 2021/3/19
 */
@Service
public class PixivServiceImpl implements PixivService {

    private static final String ORIGINAL_URL = "i.pximg.net";
    private static final String PROXY_URL = "i.pixiv.cat";

    private static final String BODY = "body";
    private static final String USER_ILLUSTS = "userIllusts";

    private static final String ILLUST_API = "https://www.pixiv.net/ajax/illust/";
    private static final String PIXIV_IMAGE = "https://i.pximg.net/";

    private static final String FAVICON = "favicon.ico";

    //https://i.neow.cc/static/favicon.ico

    /**
     * Url 分割
     */
    private static final int URL_SUBSTRING = 6;

    @Override
    public String getIllustInfo(String id, IllustGetConfig illustGetConfig) {
        String resBody = HttpRequest.get(ILLUST_API + id)
                .setHttpProxy("127.0.0.1", 7890)
                .execute()
                .body();
        JSONObject body = JSONUtil.parseObj(resBody).getJSONObject(BODY);
        body.remove(USER_ILLUSTS);

        String data = body.toString();
        if (illustGetConfig.isUseProxy()) {
            data = data.replaceAll(ORIGINAL_URL, PROXY_URL);
        }
        return data;
    }

    @Override
    public InputStream proxyPixivImage(HttpServletRequest request, HttpServletResponse response) {
        String url = getUrl(request.getRequestURI());
        HttpResponse httpResponse = HttpRequest.get(PIXIV_IMAGE + url)
                .setHttpProxy("127.0.0.1", 7890)
                .header("referer", "https://www.pixiv.net/")
                .execute();
        response.setContentType(httpResponse.header("content-type"));
        return httpResponse.bodyStream();
    }

    private String getUrl(String originalUrl) {
        originalUrl = originalUrl.substring(originalUrl.indexOf("proxy/"));
        return originalUrl.substring(URL_SUBSTRING);
    }
}
