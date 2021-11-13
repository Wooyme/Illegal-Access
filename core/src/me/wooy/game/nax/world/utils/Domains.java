package me.wooy.game.nax.world.utils;

import java.util.ArrayList;
import java.util.List;

public class Domains {
    private static final List<String> domains = new ArrayList<>();
    static {
        domains.add("www.google.com");
        domains.add("www.facebook.com");
        domains.add("www.youtube.com");
        domains.add("www.twitter.com");
        domains.add("www.instagram.com");
        domains.add("www.reddit.com");
        domains.add("www.wikipedia.org");
        domains.add("www.baidu.com");
        domains.add("www.taobao.com");
        domains.add("www.tmall.com");
        domains.add("www.amazon.com");
        domains.add("www.yahoo.com");
        domains.add("www.live.com");
        domains.add("www.linkedin.com");
        domains.add("www.weibo.com");
        domains.add("www.wechat.com");
        domains.add("www.alipay.com");
        domains.add("www.jd.com");
        domains.add("www.tianya.com");
        domains.add("www.zhihu.com");
        domains.add("www.douban.com");
        domains.add("www.sohu.com");
        domains.add("www.sina.com");
        domains.add("www.pixiv.net");
        domains.add("www.v2ex.com");
        domains.add("www.github.com");
        domains.add("www.stackoverflow.com");
        domains.add("www.csdn.net");
        domains.add("www.oschina.net");
        domains.add("www.jianshu.com");
    }
    public static List<String> getList(){
        return domains;
    }
}
