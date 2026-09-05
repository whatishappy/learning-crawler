package com.crawler.crawler_demo.HttpLearning;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//携带参数post请求
public class HttpPostParamTest {
    public static void main(String[] args) {
        //创建client对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建httpPost请求
        HttpPost httpPost = new HttpPost("http://www.baidu.com");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("key1","value1"));
        params.add(new BasicNameValuePair("key2","value2"));

        //将参数编码转化为表单
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
        httpPost.setEntity(entity);

        //获取response
        CloseableHttpResponse response = null;
        try {
            //解析响应
            response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200){
                String context = EntityUtils.toString(response.getEntity());
                System.out.println(context.replaceAll(">", ">\n"));
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
