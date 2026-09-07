package com.crawler.crawler_demo.JsoupDemo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Douban_Titlecrawler {
    public static void main(String[] args) throws IOException {
    // 强制使用IPv4，解决Java IPv6优先导致的连接超时
        System.setProperty("java.net.preferIPv4Stack", "true");

        String url = "https://movie.douban.com/top250";

        Document doc = Jsoup.connect(url)
                // 1. 用PC端Chrome的真实UA，比移动端通过率高
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36")
                // 2. 补全浏览器标准请求头，模拟真实访问
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                .header("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                .header("Referer", "https://movie.douban.com/")
                .header("Upgrade-Insecure-Requests", "1")
                // 3. 分开设置超时：连接超时15秒，读取超时20秒
                .timeout(20000)
                .get();

        //测试抓取标题
        Element titleElement = doc.selectFirst("#content h1");
        if (titleElement != null) {
            String h1Text = titleElement.text();
            System.out.println("抓取到的标题：" + h1Text);
        } else {
            System.out.println("未找到目标元素，页面可能被拦截了");
            // 调试用：打印返回的页面标题，判断是不是被反爬了
            System.out.println("返回页面标题：" + doc.title());
        }
    }
}
