package com.crawler.crawler_demo.JsoupDemo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Douban_crawler {
    public static void main(String[] args) {
        // 豆瓣Top250的URL，通过start参数控制分页，每页25条
        String baseUrl = "https://movie.douban.com/top250?start=";

        // 循环10次，爬取10页，即全部250部电影
        for (int i = 0; i < 10; i++) {
            int page = i * 25;
            String url = baseUrl + page;
            System.out.println("正在爬取第 " + (i + 1) + " 页，URL: " + url);
            crawlMovies(url);

            // 暂停1-2秒，避免请求过快被封锁[reference:0]
            try {
                Thread.sleep(1000 + (int)(Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void crawlMovies(String url) {
        try {
            // 1. 模拟浏览器发起GET请求[reference:1][reference:2]
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .timeout(10000) // 超时时间10秒
                    .get();

            // 2. 解析HTML，获取所有电影条目[reference:3]
            // 这里使用更通用的选择器 ".grid_view .item"
            Elements items = doc.select(".grid_view .item");

            // 3. 遍历并提取数据[reference:4][reference:5]
            for (Element item : items) {
                // 电影排名
                String rank = item.select(".pic em").text();
                // 电影中文名（取第一个span）
                String title = item.select(".title").first().text();
                // 电影评分
                String rating = item.select(".rating_num").text();
                // 评价人数
                String people = item.select(".star span").last().text();
                // 电影简介（可能为空，需判空）
                String quote = item.select(".quote .inq").text();

                // 打印结果
                System.out.println("排名: " + rank);
                System.out.println("电影名: " + title);
                System.out.println("评分: " + rating);
                System.out.println("评价人数: " + people);
                System.out.println("简评: " + (quote.isEmpty() ? "无" : quote));
                System.out.println("-----------------------------");
            }

        } catch (IOException e) {
            System.err.println("请求失败: " + e.getMessage());
        }
    }
}
