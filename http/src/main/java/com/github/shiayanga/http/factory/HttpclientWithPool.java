package com.github.shiayanga.http.factory;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author LiYang
 */
@Slf4j
public class HttpclientWithPool {
    private CloseableHttpClient httpClient;


    private void initHttpClient() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        // 总连接池数量
        poolingHttpClientConnectionManager.setMaxTotal(11);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(2000)
                .setSocketTimeout(3000)
                .build();

        StandardHttpRequestRetryHandler retryHandler = new StandardHttpRequestRetryHandler();
        httpClient = HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(retryHandler)
                .build();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    poolingHttpClientConnectionManager.closeExpiredConnections();
                    poolingHttpClientConnectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5000L);

    }

    public void run(){
        HttpGet httpGet = new HttpGet("https://www.baidu.com");


        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            log.info(entity.toString());
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HttpclientWithPool httpclientWithPool = new HttpclientWithPool();
        httpclientWithPool.initHttpClient();
        int cycles = 5;
        for (int i = 0; i < cycles; i++) {
            httpclientWithPool.run();
        }
    }

}
