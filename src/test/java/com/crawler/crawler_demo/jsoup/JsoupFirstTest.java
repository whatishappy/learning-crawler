package com.crawler.crawler_demo.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;   // 注意导入的是 JUnit 5 的 Test

import java.io.File;
import java.net.URL;

public class JsoupFirstTest {

    @Test  // 这个注解来自 JUnit 5
    public void testUrl() throws Exception {
        Document document = Jsoup.parse(new URL("https://www.baidu.com"), 1000);
        Element title = document.getElementsByTag("title").first();
        System.out.println(title.text());
    }

    @Test
    public void testFile() throws Exception{
        //解析文件"
        Document document = Jsoup.parse(new File("F:\\vscode\\js基础.html"), "utf-8");


        String title = document.getElementsByTag("title").first().text();

        System.out.println(title);
    }

    @Test
    public void testDOM() throws Exception{
        //获取文件，获取Document对象
        Document document = Jsoup.parse(new File("F:\\vscode\\js基础.html"), "utf-8");

        //获取元素
        Element elementById = document.getElementById("city_bj");   //id获取单个dom对象

        Elements elementsByTag = document.getElementsByTag("span");//通过标签名获取

        Elements elementsByClass = document.getElementsByClass("class_a");      //通过class属性获取

        Element element = document.getElementsByAttributeValue("href", "http://sh.itcast").first();      //通过属性名和属性值获取
    }

    @Test
    public void testData() throws Exception{
        //获取解析文件，获取Docoumnet

        //根据id获取
        Document document = Jsoup.parse(new File("F:\\vscode\\js基础.html"));

        //根据id获取元素
        Element elementById = document.getElementById("test");

        String str = elementById.id();  //从元素中获取id

        String classment = elementById.className();     //从元素中获取class

        String attr = elementById.attr("test");     //获取元素中获取属性值attr

        Attribute aClass = elementById.attribute("class");      //从元素中获取属性值attrbutes

        String text = elementById.text();           //从元素中获取文本内容text

        System.out.println(str);
        System.out.println(classment);
        System.out.println(attr);
        System.out.println(aClass);
        System.out.println(text);

    }
}