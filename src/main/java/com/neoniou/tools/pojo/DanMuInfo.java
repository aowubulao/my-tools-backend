package com.neoniou.tools.pojo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import lombok.Data;

/**
 * @author Neo.Zzj
 * @date 2021/2/15
 */
@Data
public class DanMuInfo {

    public static final String D_TAG = "</d>";

    public static final String XML_SPLIT = "\\b(p=).*?(</d)\\b";

    public static final String CONTENT_PREFIX = "\">";
    public static final String CONTENT_SUFFIX = "</";
    public static final String PARAM_SUFFIX = "p=\"";
    public static final String SPLIT = ",";
    public static final String POINT = ".";

    private static final int MINUTE = 60;
    private static final int HOUR = 3600;

    private String appearTime;

    private String type;

    private String font;

    private String color;

    private String sendTime;

    private String crcHash;

    private String content;

    public DanMuInfo(String appearTime, String type, String font, String color, String sendTime, String crcHash, String content) {
        this.appearTime = appearTime;
        this.type = type;
        this.font = font;
        this.color = color;
        this.sendTime = sendTime;
        this.crcHash = crcHash;
        this.content = content;
    }

    public static String generateAppearTime(String time) {
        int second = Integer.parseInt(time.substring(0, time.indexOf(POINT)));
        StrBuilder sb = new StrBuilder();
        if (second > HOUR) {
            int hour = second / HOUR;
            sb.append(hour).append("时");
            second -= HOUR;
        }
        if (second > MINUTE) {
            int minute = second / MINUTE;
            sb.append(minute).append("分");
            second -= MINUTE;
        }
        sb.append(second).append("秒");
        return sb.toString();
    }

    public static String generateSendTime(String timeStamp) {
        return DateUtil.date(Long.parseLong(timeStamp) * 1000L).toString();
    }

    @Override
    public String toString() {
        return "{\"appearTime\":\"" + appearTime + "\"" +
               ",\"type\":\"" + type + "\"" +
               ",\"font\":\"" + font + "\"" +
               ",\"color\":\"" + color + "\"" +
               ",\"sendTime\":\"" + sendTime + "\"" +
               ",\"crcHash\":\"" + crcHash + "\"" +
               ",\"content\":\"" + content + "\"}";
    }
}
