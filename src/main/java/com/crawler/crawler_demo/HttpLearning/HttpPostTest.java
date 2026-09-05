package com.crawler.crawler_demo.HttpLearning;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpPostTest {
    public static void main(String[] args) {
        //创建client对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建httpPost请求
        HttpPost httpPost = new HttpPost("http://www.baidu.com");
        //获取response
        CloseableHttpResponse response = null;
        try {
            //解析响应
            response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200){
                String context = EntityUtils.toString(response.getEntity());
                System.out.println(context);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            //关闭client对象与respons请求
            try {
                response.close();
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
