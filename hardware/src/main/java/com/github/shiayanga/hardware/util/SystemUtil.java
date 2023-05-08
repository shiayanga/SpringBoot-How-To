package com.github.shiayanga.hardware.util;

import com.sun.management.OperatingSystemMXBean;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 系统信息工具类
 */
public class SystemUtil {
    private SystemUtil(){}
    public static double getCpuUsage() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor processor = hardware.getProcessor();
        double[] processorCpuLoad = processor.getProcessorCpuLoad(100);
        long count = Arrays.stream(processorCpuLoad).count();
        return count/processorCpuLoad.length;
        // return processor.getSystemCpuLoad(10);
    }

    public static long getMemoryUsage() throws InterruptedException {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double processCpuLoad = osmxb.getProcessCpuLoad();
        double systemCpuLoad = osmxb.getSystemCpuLoad();
        System.out.println("systemCpuLoad:" + systemCpuLoad);
        System.out.println("processCpuLoad:" + processCpuLoad);
        // 总的物理内存
        String totalMemorySize = new DecimalFormat("#.##").format(osmxb.getTotalPhysicalMemorySize() / 1024.0 / 1024 / 1024) ;
        double totalMemorySize1 = osmxb.getTotalPhysicalMemorySize() / 1024.0 / 1024 / 1024;
        // 剩余的物理内存
        String freePhysicalMemorySize = new DecimalFormat("#.##").format(osmxb.getFreePhysicalMemorySize() / 1024.0 / 1024 / 1024);
        // 已使用的物理内存
        String usedMemory = new DecimalFormat("#.##").format((osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / 1024.0 / 1024 / 1024);
        double usedMemory1 = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / 1024.0 / 1024 / 1024;

        System.err.println("总的物理内存:" + totalMemorySize + "G");
        System.err.println("剩余的物理内存:" + freePhysicalMemorySize + "G");
        System.err.println("已使用的物理内存:" + usedMemory + "G");


        System.err.println("物理内存利用率：" + new DecimalFormat("#.##%").format(usedMemory1 / totalMemorySize1));
        System.err.println("===========================");


        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        TimeUnit.SECONDS.sleep(2);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        System.err.println("cpu核数:" + processor.getLogicalProcessorCount());
        System.err.println("cpu系统使用率:" + new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu));
        System.err.println("cpu用户使用率:" + new DecimalFormat("#.##%").format(user * 1.0 / totalCpu));
        System.err.println("cpu当前等待率:" + new DecimalFormat("#.##%").format(iowait * 1.0 / totalCpu));
        System.err.println("cpu当前空闲率:" + new DecimalFormat("#.##%").format(idle * 1.0 / totalCpu));


        long cpuUtilization = user + nice + cSys  + iowait + irq + softirq + steal;
        System.err.println("cpu利用率：" + new DecimalFormat("#.##%").format(cpuUtilization * 1.0 / totalCpu));
        return 0;
    }

}
