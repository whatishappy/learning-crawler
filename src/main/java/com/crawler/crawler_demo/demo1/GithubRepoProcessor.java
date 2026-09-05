package com.crawler.crawler_demo.demo1;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoProcessor implements PageProcessor {
    // 1. 抓取网站的相关配置，包括编码、抓取间隔、重试次数等等
    private final Site site = Site.me()
            .setRetryTimes(3)   //重试次数
            .setSleepTime(100); //出去间隔

    @Override
    // 2.爬虫逻辑
    public void process(Page page) {
        //从页面发现后续的url地址抓取

        page.addTargetRequests(page.getHtml().links()
                .regex("(https://github\\.com/\\w+/\\w+)")
                .all());

        //定义如何抽取网页信息，保存下来
        page.putField("author", page.getUrl()
                .regex("https://github\\.com/(\\w+)/.*")
                .toString());
        page.putField("name", page.getHtml()
                .xpath("//h1[@class='public']/strong/a/text()")
                .toString());
    }

    @Override
    public Site getSite() {
        return site;
    }


        public static void main(String[] args) {
            Spider.create(new GithubRepoProcessor())
                    //起始url
                    .addUrl("https://github.com/code4craft")
                    //保存格式
                    .addPipeline(new ConsolePipeline())
                    .thread(5)
                    .run();
        }
}
