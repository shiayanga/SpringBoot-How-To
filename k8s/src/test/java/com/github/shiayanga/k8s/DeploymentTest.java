package com.github.shiayanga.k8s;

import com.github.shiayanga.k8s.deployment.Deployment;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Status;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeploymentTest {
    @Test
    public void createDeploymentTest() throws ApiException {
        Deployment deployment = new Deployment();
        V1Deployment v1Deployment = deployment.createDeployment("default","nettools-test");
        System.out.println(
                v1Deployment.getStatus()
        );
    }

    @Test
    public void deleteDeploymentTest() throws ApiException {
        Deployment deployment = new Deployment();
        V1Status v1Status = deployment.deleteDeployment("default", "nettools-test");
        System.out.println(
                v1Status
        );
    }
    @Test
    public void getDeploymentTest() throws ApiException {
        Deployment deployment = new Deployment();
        V1Deployment v1Deployment = deployment.getDeployment("default","nettools-test");
        System.out.println(
                v1Deployment
        );
    }


}
