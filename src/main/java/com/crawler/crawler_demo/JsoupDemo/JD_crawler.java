package com.crawler.crawler_demo.JsoupDemo;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/*实战：  爬取京东“固态硬盘”商品消息*/
public class JD_crawler {

    public static void main(String[] args) throws IOException {
        //TODO1.构造搜索URL与keyword搜索词"显卡"
        String keyword= "固态硬盘";
        String url ="https://search.jd.com/Search?keyword="+keyword;

        //TODO2.发起请求，获取网页
        //设置User-Agent，模拟真实浏览器
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " + "Chrome/91.0.4472.124 Safari/537.36")
                .timeout(10000)
                .get();

        //TODO3. 打印网页标题检查是否成功
        System.out.println(document.title()); //固态硬盘 - 商品搜索 - 京东

    }

}
