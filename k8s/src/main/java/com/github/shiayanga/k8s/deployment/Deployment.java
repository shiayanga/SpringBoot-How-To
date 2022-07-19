package com.github.shiayanga.k8s.deployment;

import com.alibaba.fastjson.JSONObject;
import com.github.shiayanga.k8s.util.Client;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.JSON;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Deployment {
    private final ApiClient apiClient = Client.getInstance();

    public V1Deployment createDeployment(String namespace, String name) throws ApiException {
        AppsV1Api appsV1Api = new AppsV1Api(apiClient);
        /*V1Deployment deployment = this.getDeployment(namespace, name);
        if (deployment != null){
            return deployment;
        }*/

        // Metadata
        V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();
        v1ObjectMeta.setName(name);
        v1ObjectMeta.setNamespace(namespace);
        v1ObjectMeta.setUid(UUID.randomUUID().toString().replaceAll("-", ""));

        // spec
        V1LabelSelector v1LabelSelector = new V1LabelSelector();
        HashMap<String, String> labels = new HashMap<>();
        labels.put("app", name);
        v1LabelSelector.setMatchLabels(labels);

        V1PodTemplateSpec v1PodTemplateSpec = new V1PodTemplateSpec();
        V1PodSpec v1PodSpec = new V1PodSpec();
        V1Container container = new V1Container();
        container.setName(name);
        container.setImage("harbor.dcos.ncmp.unicom.local/common/nettools:1.0.0");
        container.setTerminationMessagePath("/dev/termination-log");
        container.setTerminationMessagePolicy("File");
        container.setImagePullPolicy("IfNotPresent");
        v1PodSpec.setContainers(new ArrayList<V1Container>() {{
            add(container);
        }});
        v1PodTemplateSpec.setSpec(v1PodSpec);
        V1ObjectMeta v1PodTemplateSpecMeta = new V1ObjectMeta();
        v1PodTemplateSpecMeta.setLabels(new HashMap<String, String>() {{
            put("app", name);
        }});
        v1PodTemplateSpec.setMetadata(v1PodTemplateSpecMeta);


        V1DeploymentSpec v1DeploymentSpec = new V1DeploymentSpec();

        v1DeploymentSpec.setSelector(v1LabelSelector);
        v1DeploymentSpec.setTemplate(v1PodTemplateSpec);


        V1Deployment v1Deployment = new V1Deployment();
        v1Deployment.setKind("Deployment");
        v1Deployment.setMetadata(v1ObjectMeta);
        v1Deployment.setSpec(v1DeploymentSpec);

        String string = JSONObject.toJSONString(v1Deployment);
        System.out.println(string);

        V1Deployment aTrue = appsV1Api.createNamespacedDeployment(namespace, v1Deployment, null, null, null, null);
        return aTrue;
    }

    public V1Status deleteDeployment(String namespace, String name) throws ApiException {
        AppsV1Api appsV1Api = new AppsV1Api(apiClient);
        V1Status v1Status = appsV1Api.deleteNamespacedDeployment(name, namespace, null, null, null, null, null, null);
        return v1Status;
    }

    public V1Deployment getDeployment(String namespace,String name) {
        AppsV1Api appsV1Api = new AppsV1Api(apiClient);
        V1Deployment v1Deployment = null;
        try {
            v1Deployment = appsV1Api.readNamespacedDeployment(name, namespace, "true");

        } catch (ApiException e) {
            System.out.println(e.getCode());
            System.out.println(e.getResponseBody());
            throw new RuntimeException(e);
        }
        return v1Deployment;
    }
}
