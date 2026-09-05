package com.crawler.crawler_demo.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;   // 注意导入的是 JUnit 5 的 Test
import java.net.URL;

public class JsoupFirstTest {

    @Test  // 这个注解来自 JUnit 5
    public void testUrl() throws Exception {
        Document document = Jsoup.parse(new URL("https://www.baidu.com"), 1000);
        Element title = document.getElementsByTag("title").first();
        System.out.println(title.text());
    }
}