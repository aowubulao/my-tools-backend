package com.neoniou.tools.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.neoniou.tools.controller.pojo.DanMuFilter;
import com.neoniou.tools.pojo.DanMuInfo;
import com.neoniou.tools.pojo.PageList;
import com.neoniou.tools.service.BiliService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * @author Neo.Zzj
 * @date 2021/2/14
 */
@Service
public class BiliServiceImpl implements BiliService {

    private static final String AV = "av";
    private static final String SS = "ss";
    private static final String EP = "ep";
    private static final String AV_REGEX = "^[0-9]*$";

    private static final String BILI_VIDEO_INFO = "http://api.bilibili.com/x/web-interface/view?";

    private static final String ANIME_INFO = "http://api.bilibili.com/pgc/view/web/season?";

    private static final String VIDEO_PAGE_LIST = "http://api.bilibili.com/x/player/pagelist?";

    private static final String XML_DAN_MU = "http://api.bilibili.com/x/v1/dm/list.so?oid=";

    private static final String CODE = "code";
    private static final String DATA = "data";
    private static final String PIC = "pic";
    private static final String RESULT = "result";
    private static final String EPISODES = "episodes";
    private static final String CID = "cid";
    private static final String LONG_TITLE = "long_title";
    private static final String PART = "part";

    private static final int ZERO = 0;

    @Override
    public String getCover(String code) {
        String body = HttpRequest.get(BILI_VIDEO_INFO + getCommonSuffix(code))
                .execute()
                .body();
        JSONObject resJson = JSONUtil.parseObj(body);
        if (resJson.getInt(CODE) != ZERO) {
            return null;
        } else {
            return resJson.getJSONObject(DATA).getStr(PIC);
        }
    }

    @Override
    public List<PageList> getPageList(String code) {
        List<PageList> pageLists = new ArrayList<>();

        if (code.contains(SS) || code.contains(EP)) {
            String body = HttpRequest.get(ANIME_INFO + getAnimeSuffix(code))
                    .execute()
                    .body();
            JSONObject resJson = JSONUtil.parseObj(body);
            if (resJson.getInt(CODE) != ZERO) {
                return null;
            } else {
                JSONArray episodes = resJson.getJSONObject(RESULT).getJSONArray(EPISODES);
                for (int i = 0; i < episodes.size(); i++) {
                    JSONObject episode = episodes.getJSONObject(i);
                    pageLists.add(new PageList(episode.getStr(LONG_TITLE), episode.getStr(CID)));
                }
            }
        } else {
            String body = HttpRequest.get(VIDEO_PAGE_LIST + getCommonSuffix(code))
                    .execute()
                    .body();
            JSONObject resJson = JSONUtil.parseObj(body);
            if (resJson.getInt(CODE) != ZERO) {
                return null;
            } else {
                JSONArray parts = resJson.getJSONArray(DATA);
                for (int i = 0; i < parts.size(); i++) {
                    JSONObject part = parts.getJSONObject(i);
                    String partStr = part.getStr(PART);
                    pageLists.add(new PageList("".equals(partStr) ? ( i + "p") : partStr,
                            part.getStr(CID)));
                }
            }
        }
        return pageLists;
    }

    @Override
    public List<DanMuInfo> getDanMu(DanMuFilter filter) {
        String body = HttpRequest.get(XML_DAN_MU + filter.getCid())
                .execute()
                .body();
        if (body.contains(DanMuInfo.D_TAG)) {
            List<String> danMuList = ReUtil.findAll(DanMuInfo.XML_SPLIT, body, 0);
            List<DanMuInfo> list = new ArrayList<>(danMuList.size());
            for (String danMu : danMuList) {
                String content = danMu.substring(danMu.indexOf(DanMuInfo.CONTENT_PREFIX) + 2, danMu.indexOf(DanMuInfo.CONTENT_SUFFIX));
                if (filter.getContent() != null && !content.contains(filter.getContent())) {
                    continue;
                }
                String param = danMu.substring(danMu.indexOf(DanMuInfo.PARAM_SUFFIX) + 3, danMu.indexOf(DanMuInfo.CONTENT_PREFIX));

                String[] params = param.split(DanMuInfo.SPLIT);
                String appearTime = DanMuInfo.generateAppearTime(params[0]);
                String sendTime = DanMuInfo.generateSendTime(params[4]);

                DanMuInfo danMuInfo = new DanMuInfo(appearTime, params[0], params[2], params[3], sendTime, params[6], content);
                list.add(danMuInfo);
            }
            list.sort(Comparator.comparingDouble(x -> Double.parseDouble(x.getType())));
            return list;
        }

        return null;
    }

    private String getCommonSuffix(String code) {
        String suffix;
        if (code.toLowerCase(Locale.ROOT).contains(AV)) {
            suffix = "aid=" + code.substring(2);
        } else if (code.matches(AV_REGEX)) {
            suffix = "aid=" + code;
        } else {
            suffix = "bvid=" + code;
        }
        return suffix;
    }

    private String getAnimeSuffix(String code) {
        String suffix;
        String num = code.substring(2);
        if (code.contains(SS)) {
            suffix = "season_id=" + num;
        } else {
            suffix = "ep_id=" + num;
        }
        return suffix;
    }
}
