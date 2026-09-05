## 实验一：创建百度http连接，返回网页信息

**步骤：创建httpclient对象 -> 创建HttpGet请求(传入url) -> 获取response（httpclient.execute(httpGet)）-> 解析response -> 关闭client连接与httpget连接 **



```java
public class HttpGetTest {
    public static void main(String[] args) {

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建httpGet对象，设置url访问请求
        HttpGet httpGet = new HttpGet("http://www.baidu.com");

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
```



## 实验二：携带参数POST请求访问百度

**步骤：创建httpclient对象 -> 创建参数列表 - > 创建HttpPostt请求(传入url) -> 获取response（httpclient.execute(httpPost)）-> 解析response -> 关闭client连接与httpget连接 **

```java
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
```