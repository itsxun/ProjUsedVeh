package xin.fallen.ProjUsedVeh.domain.ftp;

public class FTPConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private boolean passiveMode;
    private int clientTimeout;
    private int transFileType;
    private int transBufferSize;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPassiveMode() {
        return passiveMode;
    }

    public void setPassiveMode(boolean passiveMode) {
        this.passiveMode = passiveMode;
    }

    public int getClientTimeout() {
        return clientTimeout;
    }

    public void setClientTimeout(int clientTimeout) {
        this.clientTimeout = clientTimeout;
    }

    public int getTransFileType() {
        return transFileType;
    }

    public void setTransFileType(int transFileType) {
        this.transFileType = transFileType;
    }

    public int getTransBufferSize() {
        return transBufferSize;
    }

    public void setTransBufferSize(int transBufferSize) {
        this.transBufferSize = transBufferSize;
    }

    public String getEncoding() {
        return "UTF-8";
    }
}