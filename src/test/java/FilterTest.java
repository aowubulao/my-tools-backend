import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.junit.Test;

/**
 * @author Neo.Zzj
 * @date 2020/7/14
 */
public class FilterTest {

    private String cookie = "LIVE_BUVID=AUTO8415860803755131; _uuid=600C885C-6B7C-A15F-CCE6-33673512A7F776337infoc; buvid3=BF4840CF-67FF-4AC6-A768-CED379AA2C8B155829infoc; CURRENT_FNVAL=16; rpdid=|(ku|kRRJl~u0J'ul)Y|)|R|R; DedeUserID=5566130; DedeUserID__ckMd5=c1de42c681dfa54f; SESSDATA=6c7a5c4f%2C1601641561%2C48460*41; bili_jct=09649a7cba4a6d6b2fa68abe095cf1d8; CURRENT_QUALITY=120; _ga=GA1.2.193162754.1592223820; bp_t_offset_5566130=411224225036224324; bp_video_offset_5566130=411787965262191946; _dfcaptcha=a2ee807f49837f4033ff040f39a08b2e; PVID=23; sid=4hpimcd6; bsource=search_baidu; bfe_id=cade757b9d3229a3973a5d4e9161f3bc";

    @Test
    public void testAdd() {
        HttpResponse response = HttpRequest.post("https://api.bilibili.com/x/dm/filter/user/add")
                .header("user-agent", "Mozilla/5.0 (Linux; Android 6.0; " +
                        "Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Mobile Safari/537.36")
                .header("cookie", cookie)
                .header("origin", "https://www.bilibili.com")
                .body("type", "0")
                .body("filter", "qwe1234123")
                .body("jsonp", "jsonp")
                .body("csrf", "09649a7cba4a6d6b2fa68abe095cf1d8")
                .execute();

        System.out.println(response);
    }
}
