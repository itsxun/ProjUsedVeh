package xin.fallen.ProjUsedVeh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xin.fallen.ProjUsedVeh.config.StaticConfig;
import xin.fallen.ProjUsedVeh.util.JsonResultUtil;
import xin.fallen.ProjUsedVeh.vo.JsonResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Author: PicUpload
 * Date: 2017/5/24
 * Time: 20:23
 * Usage:
 */
@RestController
@RequestMapping("/file")
public class FileCtrl {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JsonResult fileUpload(@RequestParam("file") MultipartFile tmpFile) {
        if (tmpFile.isEmpty()) {
            return JsonResultUtil.resDispatcher("上传文件为空", 0);
        }
        String tmp = tmpFile.getOriginalFilename();
        tmp = tmp.indexOf(".") > 0 ? tmp.substring(tmp.indexOf(".")) : "";

        File file = new File(StaticConfig.FileUploadPath + File.separator + sdf.format(new Date()));
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file=new File(file + File.separator + UUID.randomUUID().toString() + tmp);
        String url=file.getAbsolutePath().replace(StaticConfig.FilePathReplacement,StaticConfig.FileUrlPre);
        try {
        tmpFile.transferTo(file);
        } catch (IOException e) {
            file.delete();
            log.error("文件上传失败，原因是：{}", e.getMessage());
            return JsonResultUtil.resDispatcher("文件上传失败", 0);
        } finally {
            tmpFile = null;
            file = null;
        }
        return JsonResultUtil.resDispatcher("上传成功："+url, 1);
    }
}
