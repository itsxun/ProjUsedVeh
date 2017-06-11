package xin.fallen.ProjUsedVeh.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import xin.fallen.ProjUsedVeh.domain.ftp.FTPConfig;
import xin.fallen.ProjUsedVeh.domain.ftp.FtpClientPool;

import java.io.File;
import java.io.FileInputStream;

/**
 * Author: Fallen
 * Date: 2017/6/11
 * Time: 13:15
 * Usage:
 */
public class FtpUtil {
    public static boolean upload(FTPClient ftpClient, FTPConfig config, File source, String targetPath) {
        boolean flag = false;
        try {
            ftpClient.connect(config.getHost(), config.getPort());
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                throw new RuntimeException("ftpClient与服务器建立连接异常");
            }
            flag = ftpClient.login(config.getUsername(), config.getPassword());
            if (!flag) {
                throw new RuntimeException("ftpClient登陆失败! userName:" + config.getUsername() + " ; password:" + config.getPassword());
            }
            ftpClient.setFileType(config.getTransFileType());
            ftpClient.setBufferSize(config.getTransBufferSize());
            flag = ftpClient.storeFile(targetPath, new FileInputStream(source));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return flag;
    }

    public static boolean upload(FtpClientPool pool, FTPConfig config, File source, String targetPath) {
        boolean flag = false;
        FTPClient ftpClient = null;
        try {
            ftpClient = pool.borrowObject();
            ftpClient.connect(config.getHost(), config.getPort());
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                throw new RuntimeException("ftpClient与服务器建立连接异常");
            }
            flag = ftpClient.login(config.getUsername(), config.getPassword());
            if (!flag) {
                throw new RuntimeException("ftpClient登陆失败! userName:" + config.getUsername() + " ; password:" + config.getPassword());
            }
            ftpClient.setFileType(config.getTransFileType());
            ftpClient.setBufferSize(config.getTransBufferSize());
            ftpClient.setControlEncoding(config.getEncoding());
            flag = ftpClient.storeFile(targetPath, new FileInputStream(source));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            if (ftpClient != null) {
                try {
                    pool.returnObject(ftpClient);
                } catch (Exception e) {
                    System.out.println("FtpClient归还连接池异常，原因是：" + e.getMessage());
                }
            }
        }
        return flag;
    }
}
