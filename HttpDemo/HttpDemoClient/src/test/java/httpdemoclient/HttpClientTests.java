package httpdemoclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class HttpClientTests {

    @Test
    void testGet() throws IOException {

        // CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://localhost:8080/ping");
        RequestConfig config = RequestConfig.custom()
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)
                // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)
                // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(true)
                .build();
        httpGet.setConfig(config);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity);

        response.close();
        httpClient.close();
    }

    @Test
    void testGetWithParams() throws IOException, URISyntaxException {
        // 将参数放入键值对类NameValuePair中,再放入集合中
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", "jape"));
        params.add(new BasicNameValuePair("age", "18"));

        // 设置uri信息,并将参数集合放入uri;
        URI uri = new URIBuilder().setScheme("http").setHost("localhost")
                .setPort(12345).setPath("/ping")
                .setParameters(params)
                // 注:这里也支持一个键值对一个键值对地往里面放setParameter(String key, String value)
                .setParameter("name", "jape")
                .build();
    }


    @Test
    void testPost() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://localhost:8080/doPost");
        CloseableHttpResponse response = httpClient.execute(httpPost);

        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity);

        httpClient.close();
    }

    @Test
    void testPostWithParams() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://localhost:8080/doPost");
        // 把自己伪装成浏览器。
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        // 1.json
        String jsonString = "{\n" +
                "   \"name\":\"jape\"\n" +
                "}";
        StringEntity reqEntity = new StringEntity(jsonString, "UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(reqEntity);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        // 2.表单
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        parameters.add(new BasicNameValuePair("name", "jape"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);

    }

}
