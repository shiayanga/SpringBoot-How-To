package com.github.shiayanga.k8s.pod;

import com.github.shiayanga.k8s.util.Client;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1PodList;

public class Pod {
    private final ApiClient apiClient = Client.getInstance();
    public V1PodList listAllPods()throws Exception{
        Configuration.setDefaultApiClient(apiClient);
        CoreV1Api api = new CoreV1Api();
        return api.listPodForAllNamespaces(
                null, null, null,
                null, null, null,
                null, null, null, null
        );
    }




}
