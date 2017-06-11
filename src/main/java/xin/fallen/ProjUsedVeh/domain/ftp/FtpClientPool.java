package xin.fallen.ProjUsedVeh.domain.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xin.fallen.ProjUsedVeh.config.StaticConfig;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class FtpClientPool implements ObjectPool<FTPClient> {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final BlockingQueue<FTPClient> pool;
    private final FtpClientFactory factory;

    public FtpClientPool(FtpClientFactory factory) {
        this(StaticConfig.DefaultPoolSize, factory);
    }

    public FtpClientPool(int poolSize, FtpClientFactory factory) {
        this.factory = factory;
        pool = new ArrayBlockingQueue<>(poolSize * 2);
        initPool(poolSize);
    }

    private void initPool(int maxPoolSize) {
        try {
            for (int i = 0; i < maxPoolSize; i++) {
                addObject();
            }
        } catch (Exception e) {
            log.error("Ftp连接池初始化异常，原因是{} ", e.getMessage());
        }
        log.info("ftp连接池初始化完成，连接数量：" + maxPoolSize);
    }

    public FTPClient borrowObject() {
        FTPClient client = null;
        try {
            client = pool.take();
            if (client == null) {
                client = factory.makeObject();
                addObject();
            } else if (!factory.validateObject(client)) {//验证不通过
                //使对象在池中失效
                invalidateObject(client);
                //制造并添加新对象到池中
                client = factory.makeObject();
                addObject();
            }
        } catch (Exception e) {
            log.error("从连接池获取client对象失败，原因是：{}", e.getMessage());
        }
        return client;
    }

    public void returnObject(FTPClient client) throws Exception {
        if ((client != null) && !pool.offer(client, 3, TimeUnit.SECONDS)) {
            try {
                factory.destroyObject(client);
            } catch (IOException e) {
                log.error("归还ftp连接失败，原因是：{}", e.getMessage());
            }
        }
    }

    public void invalidateObject(FTPClient client) throws Exception {
        pool.remove(client);
    }

    public void addObject() throws Exception {
        pool.offer(factory.makeObject(), 3, TimeUnit.SECONDS);
    }

    public int getNumIdle() throws UnsupportedOperationException {
        return 0;
    }

    public int getNumActive() throws UnsupportedOperationException {
        return 0;
    }

    public void clear() throws Exception {

    }

    public void close() {
        while (pool.iterator().hasNext()) {
            FTPClient client = null;
            try {
                client = pool.take();
                factory.destroyObject(client);
            } catch (Exception e) {
                log.error("ftp连接池关闭异常，原因是：{}", e.getMessage());
            }
        }
    }

    public void setFactory(PoolableObjectFactory<FTPClient> factory) throws IllegalStateException, UnsupportedOperationException {

    }
}