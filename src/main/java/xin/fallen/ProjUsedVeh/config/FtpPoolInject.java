package xin.fallen.ProjUsedVeh.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import xin.fallen.ProjUsedVeh.domain.ftp.FTPConfig;
import xin.fallen.ProjUsedVeh.domain.ftp.FtpClientFactory;
import xin.fallen.ProjUsedVeh.domain.ftp.FtpClientPool;


/**
 * Author: Fallen
 * Date: 2017/6/9
 * Time: 16:52
 * Usage:
 */
@Component
@Configuration
public class FtpPoolInject {
    @Value("${ftp.ip-address}")
    public String ftpAddress;
    @Value("${ftp.port}")
    public int ftpPort;
    @Value("${ftp.username}")
    public String ftpUserName;
    @Value("${ftp.password}")
    public String ftpPassword;
    @Value("${ftp.passtive-mode}")
    public boolean ftpPasstiveMode;
    @Value("${ftp.time-out}")
    public int ftpTimeOut;
    @Value("${ftp.transBufferSize}")
    public int transBufferSize;

    @Bean(name = "ftpConfig")
    public FTPConfig ftpConfigInit() {
        FTPConfig fc = new FTPConfig();
        fc.setHost(ftpAddress);
        fc.setPort(ftpPort);
        fc.setUsername(ftpUserName);
        fc.setPassword(ftpPassword);
        fc.setPassiveMode(ftpPasstiveMode);
        fc.setClientTimeout(ftpTimeOut);
        fc.setTransBufferSize(transBufferSize);
        return fc;
    }

    @Bean(name = "ftpClientFactory")
    public FtpClientFactory ftpClientFactoryInit(@Autowired FTPConfig ftpConfig) {
        return new FtpClientFactory(ftpConfig);
    }

    @Bean(name = "ftpClientPool")
    public FtpClientPool ftpClientPoolInit(@Autowired FtpClientFactory ftpClientFactory) {
        return new FtpClientPool(ftpClientFactory);
    }
}