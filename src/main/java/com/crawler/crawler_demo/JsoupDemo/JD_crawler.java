package com.crawler.crawler_demo.JsoupDemo;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/*实战：  爬取京东“固态硬盘”商品消息*/
public class JD_crawler {

    public static void main(String[] args) throws IOException {
        //TODO1.构造搜索URL与keyword搜索词"显卡"
        String keyword= "固态硬盘";
        //String url ="https://search.jd.com/Search?keyword="+keyword;
        String url = "https://api.m.jd.com/api?appid=search-pc-java&t=1788712200383&client=pc&clientVersion=1.0.0&cthr=1&uuid=1780569736554674807823&loginType=3&keyword=%E5%9B%BA%E6%80%81%E7%A1%AC%E7%9B%98&functionId=pc_search_searchWare&body={%22enc%22:%22utf-8%22,%22pvid%22:%2271f1a02d2c7b45958fc14efcbe5accb8%22,%22from%22:%22home%22,%22area%22:%2219_1617_3644_56509%22,%22page%22:1,%22mode%22:%22%22,%22concise%22:false,%22hoverPictures%22:true,%22newAdvRepeat%22:true,%22mixerParam%22:true,%22new_interval%22:true,%22s%22:1,%22pageSize%22:30}&x-api-eid-token=jdd03WSTLC77MHCYMXPUE3MBPK33VTIXGAKBKROMSWGNXI7Q6T7QFR6ZJOFWI244YDKXYMOINMHC2ITJROBUBCZTJ4QIXLQAAAANAO6GTHWQAAAAACTI5J2XUKUAY6AX&h5st=20260907003003387;n2n5yyj22j5iv7y1;f06cc;tk03wc3b11cb518nr7LGZETGyiFSGhaOw7dUT_zjO9yahIqnDtBau22TOZYKkwywcxE2naSShJa80lRAORp_46mi3S_g;094b3ec82fb103233c17066ffa7e0c03d75b14dbb9bb397172d7d9b8ff2c3589;5.3;1788712200387;pjbMhjZfyOIOCWIR5jIRyGUe9iIQJrJdJrESJrpjh75fzfof7jIf6PodzPYfLDIj3ipjLDrgJnkYm6Xam6XPZmHjLDIj3nYOJipjLDrg7jojxjJQIeFjLrJp-j5T5f4e2bYf1jFS3r4e1L4e2nYSyPYS4Xod1Hoe0fVf3jpjxjpPl61SJW1OJrpjh7JjKS1f6T1eyfVT0jofISIS0Xld4jYd7rYTIq4e6jYd5bFjLDIj4mFO9m1TJrpjh7Jj7jpjxjpe2iFjLrJp-jZe9fIg2T0UG6VRFuWeDipjxjJOJrpjh7JjTGIPqKIbzLoexKEjLDIj_ulS9mFPJrpjh7Jj5fIQCOGjLDIjFqEjLrJp-3kjLDLjKSng2mHjLDIj4nYOJipjLrpjh7peLDIj6nYOJipjLrpjh7pe6rJdJrYf2iFjLrpjLDrgz3pjxjJf6XETJrpjLrJp-jpP3PVbjmFf3PGOJrJdJ31QHyVT5ipjLrpjh7pfLDIjzXETJrpjLrJp-rojxj5e2iFjLrpjLDrg6jojxjJe2iFjLrpjLDrg7rJdJXYOJipjLrpjh7pfLDIj3XETJrpjLrJp-PYeLDIj4XETJrpjLrJp-jJe9nIg7jpjxjZf2iFjLrpjLDrg7rJdJ-1OJrpjLrJp-Xojxj5P-ipjLrpjh7pfLDIj-ipjLrpjh7pfLDIjHOEjLrpjLD7NLDIjHyVS3KUSJrpjh7ZMLrJpJjHWUGnW5C0bm_VSVuGRAipjxjJf7ipjLrpjh-kjxjpP7ipjLDrgJHoj-WlNCqmjwXYfLbVR8ikS9mnjwLUO9GlYJrJdJnVO4ipjLD7N;e20b8d35505d1adaf7a0fdfb0c6f3740d542dffd63ba023fee401e1a1a8648f9;qbkgHGHQ8GlOIyVOF6JQ8G1P5WFW3yVSC61T-bEQGGlQI6ZNHuFT-bVR7qUT&t=1788712200393";

        //TODO2.发起请求，获取网页
        //设置User-Agent，模拟真实浏览器
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " + "Chrome/91.0.4472.124 Safari/537.36")
                .timeout(10000)
                .get();

        /*//打印网页标题检查是否成功
        System.out.println(document.title()); //固态硬盘 - 商品搜索 - 京东*/


        //TODO通过CSS选择器获取商品列表的ul标签
        Elements ul = document.select("ul.gl-warp.clearfix");
        //获取ul下所有的li，每个li代表一个商品
        Elements liList = ul.select("li.gl-item");

        for (Element li : liList) {
            //商品名称
            String name = li.select("div.p-name a").text();
            //商品价格
            Element priceElement = li.select("div.p-price i").first();
            String price= priceElement != null ?priceElement.text() : "获取商品失败";     //可能为空进行非空判断

            //商品图片连接
            String imgUrl = li.select("div.p-img a img").attr("data-lazy-img");
            if (imgUrl.isEmpty()){
                //如果图片使用src属性
                imgUrl = li.select("div.p-img a img").attr("src");
            }

            //商品详细页面链接
            String detailUrl = li.select("div.p-name a").attr("href");
            if (!detailUrl.startsWith("http")){
                detailUrl="https:" + detailUrl;
            }


            //打印结果
            System.out.println("名称: " + name);
            System.out.println("价格: " + price);
            System.out.println("图片: " + imgUrl);
            System.out.println("链接: " + detailUrl);
            System.out.println("----------------------");

        }
    }

}
