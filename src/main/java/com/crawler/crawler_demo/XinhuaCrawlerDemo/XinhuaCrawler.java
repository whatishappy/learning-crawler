package com.crawler.crawler_demo.XinhuaCrawlerDemo;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
* 新华网法制专题列表爬虫
* 抓取标题+时间，至少3页，保存至CSV文件
*
* */
public class XinhuaCrawler {
    //导入Bse_URL,后续需要拼接页数，当前页面爬取对象为“法制专题”
    private static final String Base_URL="http://www.xinhuanet.com/legal/ej.htm?page=";

    //模拟浏览器UA，后期可能需要轮换UA，避免触发反爬
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

    //保存为本地CSV文件
    private static final String OUTPUT_FILE= "xinhua_legal_data(fzzt).csv";

    public static void main(String[] args) {
        System.out.println("开始抓取新华网法治专栏列表...");

        //通过字符缓冲流写入CSV文件中
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))){
            //表头
            writer.write("序号，标题，发布时间");
            writer.newLine();//换行

            //真实url
            String url = Base_URL+"fzzt";
            //获取dom对象
            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .proxy("127.0.0.1",10215)
                    .timeout(10000) //设置超时时间
                    .get();

            //测试获取“法制专题”栏目名称
            Element targetA = doc.select("#navCont a[href*=fzzt]").first();
            if (targetA!=null){
                //如果非空打印
                System.out.println(targetA.text());
                //获取链接
                System.out.println(targetA.attr("href"));
            }else {
                System.out.println("爬取失败!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
