package xin.fallen.ProjUsedVeh.domain.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FtpClientFactory implements PoolableObjectFactory<FTPClient> {
    private static Logger log = LoggerFactory.getLogger("ftp");
    private FTPConfig config;

    public FtpClientFactory(FTPConfig config) {
        this.config = config;
    }

    public FTPClient makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(config.getClientTimeout());
        try {
            ftpClient.connect(config.getHost(), config.getPort());
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.error("FTP服务器连接失败");
                return null;
            }
            boolean result = ftpClient.login(config.getUsername(), config.getPassword());
            if (!result) {
                log.error("ftpClient登陆失败! userName:" + config.getUsername() + " ; password:" + config.getPassword());
            }
            ftpClient.setControlEncoding(config.getEncoding());
            if (config.isPassiveMode()) {
                ftpClient.enterLocalPassiveMode();
            }
        } catch (Exception e) {
            log.error("Ftp连接初始化失败，原因是：{}", e.getMessage());
            return null;
        }
        log.info("ftp连接池数量+1");
        return ftpClient;
    }

    public void destroyObject(FTPClient ftpClient) throws Exception {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.logout();
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            // 注意,一定要在finally代码中断开连接，否则会导致占用ftp连接情况
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                log.error("FtpClient断开连接异常，原因是{}", e.getMessage());
            }
        }
    }

    public boolean validateObject(FTPClient ftpClient) {
        try {
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            log.error("FtpClient验证异常，原因是{}", e.getMessage());
            throw new RuntimeException("Failed to validate client: " + e, e);
        }
    }

    public void activateObject(FTPClient ftpClient) throws Exception {
    }

    public void passivateObject(FTPClient ftpClient) throws Exception {
    }
}