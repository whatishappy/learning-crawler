package com.crawler.crawler_demo.HttpLearning;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpConfigTest {
    public static void main(String[] args) {

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建httpGet对象，设置url访问请求
        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        //设置http配置信息
        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000)          //设置连接超时时间
                .setConnectionRequestTimeout(500)           //设置获取连接最长时间
                .setSocketTimeout(10 * 1000).build();//设置数据传输最长时间

        httpGet.setConfig(config);

        CloseableHttpResponse respose = null;

        try {
            //使用HttpCilent发起请求，获取response
            respose = client.execute(httpGet);
            //解析响应
            if (respose.getStatusLine().getStatusCode()==200){
                String context = EntityUtils.toString(respose.getEntity(), "utf-8");
                System.out.println(context);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            //关闭http请求和client
            try {
                respose.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
