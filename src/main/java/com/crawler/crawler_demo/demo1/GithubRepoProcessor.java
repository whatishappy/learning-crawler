package com.crawler.crawler_demo.demo1;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoProcessor implements PageProcessor {
    private final Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(100);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links()
                .regex("(https://github\\.com/\\w+/\\w+)")
                .all());

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
                    .addUrl("https://github.com/code4craft")
                    .addPipeline(new ConsolePipeline())
                    .thread(5)
                    .run();
        }
}
