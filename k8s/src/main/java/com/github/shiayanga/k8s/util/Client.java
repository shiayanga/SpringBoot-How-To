package com.github.shiayanga.k8s.util;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.Config;


public class Client {

    private volatile static ApiClient apiClient;

    public Client() {
    }

    public static ApiClient getInstance(){
        if (apiClient == null){
            synchronized (Client.class){
                if (apiClient == null){
                    apiClient = Config.fromToken("https://12.24.28.9:4255", "11cba1387cbecec993a588ce",false);
                }
            }
        }
        return apiClient;
    }

}
