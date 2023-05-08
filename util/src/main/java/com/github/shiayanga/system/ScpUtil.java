package com.github.shiayanga.system;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.scp.client.ScpClient;
import org.apache.sshd.scp.client.ScpClientCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ScpUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScpUtil.class);

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
            logger.error("ScpUtil.copyToRemoteServer;exception occurs while connecting to target server:{}", e.getMessage());
            return;
        }
        clientSession.addPasswordIdentity(passwd);
        boolean success = false;
        try {
            success = clientSession.auth().verify().isSuccess();
        } catch (IOException e) {
            logger.error("ScpUtil.copyToRemoteServer;exception occurs while auth to target server:{}", e.getMessage());
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
                logger.error("ScpUtil.copyToRemoteServer;exception occurs while uploadFile to target server:{}", e.getMessage());
            } finally {
                if (scpClient != null) {
                    scpClient = null;
                }
                if (clientSession != null && clientSession.isOpen()) {
                    try {
                        clientSession.close();
                    } catch (IOException e) {
                        logger.error("ScpUtil.copyToRemoteServer;exception occurs while close session:{}", e.getMessage());
                    }
                }
                if (sshClient != null && sshClient.isOpen()) {
                    try {
                        sshClient.close();
                    } catch (IOException e) {
                        logger.error("ScpUtil.copyToRemoteServer;exception occurs while close SSH client:{}", e.getMessage());
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("Total Cost time: {}s.",(endTime - startTime) / 1000.0);

    }

    public static void main(String[] args) {
        scpFile("192.168.0.132",22,"sky","","D:\\mybatis-generator.zip","/home/sky/task_center");
    }
}
