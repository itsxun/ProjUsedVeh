package xin.fallen.ProjUsedVeh.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Author: Fallen
 * Date: 2017/6/10
 * Time: 18:07
 * Usage:
 */
@RestController
public class Ping {
    @RequestMapping("/ping")
    public void ping(HttpServletResponse resp) throws Exception {
        resp.getWriter().write("pong");
    }

//    public void test(HttpServletResponse resp) throws Exception {
//        FTPConfig config = new FTPConfig();
//        config.setHost("182.18.0.113");
//        config.setPort(21);
//        config.setUsername("weixinftp");
//        config.setPassword("root123");
//        config.setPassiveMode("false");
//        config.setClientTimeout(30000);
//        config.setTransFileType(FTP.BINARY_FILE_TYPE);
//        FTPClient ftpClient = new FTPClient();
//        ftpClient.setConnectTimeout(config.getClientTimeout());
//        ftpClient.connect(config.getHost(), config.getPort());
//        boolean result = ftpClient.login(config.getUsername(), config.getPassword());
//        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
//            ftpClient.disconnect();
//            System.out.println("login fail");
//            resp.getWriter().write("fail");
//            return;
//        }
//        if (!result) {
//            System.out.println("ftpClient登陆失败! userName:" + config.getUsername() + " ; password:" + config.getPassword());
//        }
//        boolean flag;
//        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//        ftpClient.setBufferSize(1024);
//        ftpClient.setControlEncoding("UTF-8");
//        ftpClient.enterLocalPassiveMode();
//        InputStream in = new FileInputStream(new File("/home/oracle/apache-tomcat/6063/test.txt"));
//        flag = ftpClient.storeFile("/weixin_net_in/test.txt", in);
//        ftpClient.logout();
//        ftpClient.disconnect();
//        if (flag) {
//            resp.getWriter().write("success");
//        } else {
//            System.out.println("上传失败");
//            resp.getWriter().write("fail");
//        }
//    }
}
