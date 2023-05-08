package com.github.shiayanga.hardware;

import com.github.shiayanga.hardware.util.SystemUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HardwareApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void system() throws InterruptedException {
        double cpuUsage = SystemUtil.getCpuUsage();
        long memoryUsage = SystemUtil.getMemoryUsage();
        System.out.println("cpuUsage: " + cpuUsage);
        System.out.println("memoryUsage: " + memoryUsage);
    }
}
