package com.github.shiayanga.k8s;

import com.github.shiayanga.k8s.pod.Pod;
import io.kubernetes.client.openapi.models.V1ListMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PodTest {

    @Test
    public void listAllPodsTest(){
        Pod pod = new Pod();
        try {
            V1PodList v1PodList = pod.listAllPods();
            List<V1Pod> items = v1PodList.getItems();
            items.forEach(item -> {
                System.out.println(item.getMetadata());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
