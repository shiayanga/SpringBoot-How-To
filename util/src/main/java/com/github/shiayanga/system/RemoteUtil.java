package com.github.shiayanga.system;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.scp.client.ScpClient;
import org.apache.sshd.scp.client.ScpClientCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemoteUtil {
    private static final Logger logger = LoggerFactory.getLogger(RemoteUtil.class);

    /**
     * 复制文件到远程服务器
     *
     * @param ip        远程服务器IP
     * @param username  远程服务器用户名
     * @param passwd    远程服务器密码
     * @param targetDir 远程服务器目标文件夹
     */
    public static void scpFile(String ip, Integer port, String username, String passwd, String sourceFile, String targetDir) {
        long startTime = System.currentTimeMillis();
        SshClient sshClient = SshClient.setUpDefaultClient();
        sshClient.start();
        ClientSession clientSession;
        try {
            clientSession = sshClient.connect(username, ip, port).verify().getSession();
        } catch (IOException e) {
            logger.error("RemoteUtil.copyToRemoteServer;exception occurs while connecting to target server:{}", e.getMessage());
            return;
        }
        clientSession.addPasswordIdentity(passwd);
        boolean success = false;
        try {
            success = clientSession.auth().verify().isSuccess();
        } catch (IOException e) {
            logger.error("RemoteUtil.copyToRemoteServer;exception occurs while auth to target server:{}", e.getMessage());
            return;
        }
        if (success) {
            ScpClientCreator scpClientCreator = ScpClientCreator.instance();
            ScpClient scpClient = scpClientCreator.createScpClient(clientSession);
            try {
                logger.info("scp beginning");
                scpClient.upload(sourceFile, targetDir, ScpClient.Option.PreserveAttributes);
                logger.info("scp finished");
            } catch (IOException e) {
                logger.error("RemoteUtil.copyToRemoteServer;exception occurs while uploadFile to target server:{}", e.getMessage());
            } finally {
                if (scpClient != null) {
                    scpClient = null;
                }
                if (clientSession != null && clientSession.isOpen()) {
                    try {
                        clientSession.close();
                    } catch (IOException e) {
                        logger.error("RemoteUtil.copyToRemoteServer;exception occurs while close session:{}", e.getMessage());
                    }
                }
                if (sshClient != null && sshClient.isOpen()) {
                    try {
                        sshClient.close();
                    } catch (IOException e) {
                        logger.error("RemoteUtil.copyToRemoteServer;exception occurs while close SSH client:{}", e.getMessage());
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("Total Cost time: {}s.",(endTime - startTime) / 1000.0);

    }

    public static String executeCommand(String ip, Integer port, String username, String passwd,String command){
        SshClient sshClient = SshClient.setUpDefaultClient();
        sshClient.start();
        ClientSession clientSession;
        try {
            clientSession = sshClient.connect(username, ip, port).verify().getSession();
        } catch (IOException e) {
            logger.error("RemoteUtil.executeCommand;exception occurs while connecting to target server:{}", e.getMessage());
            return "false";
        }
        clientSession.addPasswordIdentity(passwd);
        boolean success = false;
        try {
            success = clientSession.auth().verify().isSuccess();
        } catch (IOException e) {
            logger.error("RemoteUtil.executeCommand;exception occurs while auth to target server:{}", e.getMessage());
            return "false";
        }
        if (success){
            try {
                ChannelExec channel = clientSession.createExecChannel(command);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ByteArrayOutputStream err = new ByteArrayOutputStream();
                channel.setOut(out);
                channel.setErr(err);

                if (!channel.open().verify(10 * 1000).isOpened()) {
                    logger.error("RemoteUtil.executeCommand;open faild");
                    return "false";
                }
                List<ClientChannelEvent> list = new ArrayList<>();
                list.add(ClientChannelEvent.CLOSED);
                channel.waitFor(list, 10 * 1000);
                channel.close();
                String result = out.toString();
                String error = err.toString();
                logger.info("result:{}",result);
                logger.info("err:{}",error);
                return result;
            } catch (IOException e) {
                logger.error("RemoteUtil.executeCommand;exception occurs: {}",e.getMessage());
                return "false";
            }
        }
        return "true";
    }

    public static void main(String[] args) {
        //scpFile("192.168.0.132",22,"sky","7uji9olp-","D:\\Projects\\GeneralGateway\\task-center\\task-executor-sample\\target\\task-executor-sample.zip","/home/sky/task_center");
        executeCommand("192.168.0.132",22,"sky","7uji9olp-","unzip /home/sky/task_center/task-executor-sample.zip -d /home/sky/task_center");
        // String nodeAddress = "http://127.0.0.1:9999/";
        // String substring = nodeAddress.substring(nodeAddress.lastIndexOf("//") + 2, nodeAddress.lastIndexOf(":"));
        // System.out.println(substring);
    }


}
