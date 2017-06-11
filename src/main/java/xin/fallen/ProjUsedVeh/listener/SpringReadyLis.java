package xin.fallen.ProjUsedVeh.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import xin.fallen.ProjUsedVeh.config.StaticConfig;
import xin.fallen.ProjUsedVeh.util.ConfigLoader;
import xin.fallen.ProjUsedVeh.util.FileFinder;

import java.io.File;


public class SpringReadyLis implements ApplicationListener<ApplicationPreparedEvent> {
    private static Logger log = LoggerFactory.getLogger("log");

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
//        ConfigLoader.load(new File("D:\\git\\ProjUsedVeh\\src\\main\\resources\\config.xml"), StaticConfig.class);
        ConfigLoader.load(FileFinder.find("config.xml"), StaticConfig.class);
        log.info("<=================Config Load Complete==================>");
    }
}