package xin.fallen.ProjUsedVeh.controller;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.fallen.ProjUsedVeh.config.StaticConfig;
import xin.fallen.ProjUsedVeh.domain.Buyer;
import xin.fallen.ProjUsedVeh.domain.PicUpload;
import xin.fallen.ProjUsedVeh.domain.Seller;
import xin.fallen.ProjUsedVeh.util.Base64Util;
import xin.fallen.ProjUsedVeh.util.HttpUtil;
import xin.fallen.ProjUsedVeh.util.JsonResultUtil;
import xin.fallen.ProjUsedVeh.vo.Callback;
import xin.fallen.ProjUsedVeh.vo.JsonResult;

@RestController
public class PicUploadCtrl {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/new")
    public JsonResult newPicUpload(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new Gson();
        Date date = new Date();
        String values = null;
        try {
            values = IOUtils.toString(req.getInputStream());
        } catch (IOException e) {
            log.error("参数值取出异常，原因是：{}", e.getMessage());
            return JsonResultUtil.resDispatcher("参数值取出异常", 0);
        }
        PicUpload picUpload = gson.fromJson(values, PicUpload.class);
        if (picUpload == null) {
            log.error("参数值转成Bean失败，参数值为：{}", values);
            return JsonResultUtil.resDispatcher("参数值转成Bean失败，参数值为：" + values, 0);
        }
        if (picUpload.getToken() == null || picUpload.getJylsh() == null || picUpload.getType() == null || picUpload.getPic() == null) {
            return JsonResultUtil.resDispatcher("请填写完整有效参数，参数值为：" + values, 0);
        }
        File file = new File(StaticConfig.WanDirPathNew + File.separator + sdf.format(date) + File.separator + picUpload.getJylsh());
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = new File(file + File.separator + picUpload.getType() + "_" + UUID.randomUUID().toString().toUpperCase() + ".JPG");
        try {
            FileUtils.writeByteArrayToFile(file, Base64Util.decode(picUpload.getPic().replace(StaticConfig.Base64EncodePrefix, "")));
        } catch (IOException e) {
            log.error("图片生成失败，原因是：{}", e.getMessage());
            return JsonResultUtil.resDispatcher("图片写入文件失败，原因是：" + e.getMessage(), 0);
        }
        String res = file.getAbsolutePath().replace(StaticConfig.WanPathReplacementNew, StaticConfig.WanUrlReplacementNew).replace("\\", "/");
        res = StaticConfig.RemoteNotifyUrl.replace("{JYLSH}", picUpload.getJylsh()).replace("{TYPE}", picUpload.getType()).replace("{URL_WAN}", res).replace("{TOKEN}", picUpload.getToken());
        log.info("准备请求：{}", res);
        res = HttpUtil.get(res).replace("null(", "").replace(")", "");
        Callback callback = gson.fromJson(res, Callback.class);
        if ("0".equalsIgnoreCase(callback.getRes())) {
            log.error("流水号为{}，照片类型为{}的图片上传失败，原因是：{}", picUpload.getJylsh(), picUpload.getType(), callback.getMsg());
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                log.error("上传失败的文件删除失败，路径为：{}", file.getAbsolutePath());
            }
            return JsonResultUtil.resDispatcher(callback.getMsg(), 0);
        }
        return JsonResultUtil.resDispatcher(callback.getMsg());
    }

    @RequestMapping("/seller-up-pre")
    public JsonResult sellerPicUploadPre(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new Gson();
        Date date = new Date();
        String values = null;
        try {
            values = IOUtils.toString(req.getInputStream());
        } catch (IOException e) {
            log.error("参数值取出异常，原因是：{}", e.getMessage());
            return JsonResultUtil.resDispatcher("参数值取出异常，原因是：" + e.getMessage(), 0);
        }
        Seller seller = gson.fromJson(values, Seller.class);
        if (seller == null) {
            log.error("参数值转为Bean异常，参数值为：{}", values);
            return JsonResultUtil.resDispatcher("参数值转为Bean异常，参数为：" + values, 0);
        }
        if (seller.getType() == null || seller.getPic() == null || seller.getXszbh() == null) {
            return JsonResultUtil.resDispatcher("请填写完整有效信息，参数为：" + values, 0);
        }
        File file = new File(StaticConfig.WanDirPathPre + File.separator + sdf.format(date));
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = new File(file + File.separator + seller.getType() + "_" + UUID.randomUUID().toString().toUpperCase() + ".JPG");
        try {
            FileUtils.writeByteArrayToFile(file, Base64Util.decode(seller.getPic().replace(StaticConfig.Base64EncodePrefix, "")));
        } catch (IOException e) {
            log.error("图片生成异常，原因是：{}", e.getMessage());
            return JsonResultUtil.resDispatcher("图片生成异常，原因是:" + e.getMessage(), 0);
        }
        String res = file.getAbsolutePath().replace(StaticConfig.WanPathReplacementPre, StaticConfig.WanUrlReplacementPre).replace("\\", "/");
        res = StaticConfig.SellerRemoteNotifyUrlPre.replace("{TYPE}", seller.getType()).replace("{XSZBH}", seller.getXszbh()).replace("{DISK_PATH}", file.getAbsolutePath()).replace("{URL_WAN}", res);
        log.info("准备请求：{}", res);
        Callback callback = gson.fromJson(HttpUtil.get(res).replace("null(", "").replace(")", ""), Callback.class);
        if ("0".equalsIgnoreCase(callback.getRes())) {
            log.error("操作失败，原因是：{}", callback.getMsg());
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                log.error("上传失败的文件删除失败，原因是：{}，路径为：", e.getMessage(), file.getAbsolutePath());
            }
            return JsonResultUtil.resDispatcher(callback.getMsg(), 0);
        }
        return JsonResultUtil.resDispatcher(callback.getMsg(), 1);
    }

    @RequestMapping("/buyer-up-pre")
    public JsonResult buyerPicUploadPre(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new Gson();
        Date date = new Date();
        String values = null;
        try {
            values = IOUtils.toString(req.getInputStream());
        } catch (IOException e) {
            log.error("参数值取出异常，原因是：{}", e.getMessage());
            return JsonResultUtil.resDispatcher("参数值取出异常，原因是：" + e.getMessage(), 0);
        }
        Buyer buyer = gson.fromJson(values, Buyer.class);
        if (buyer == null) {
            log.error("参数值转为Bean异常，参数值为：{}", values);
            return JsonResultUtil.resDispatcher("参数值转为Bean异常，参数为：" + values, 0);
        }
        if (buyer.getType() == null || buyer.getPic() == null || buyer.getJylsh() == null) {
            return JsonResultUtil.resDispatcher("请填写完整有效信息，参数为：" + values, 0);
        }
        File file = new File(StaticConfig.WanDirPathPre + File.separator + sdf.format(date));
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = new File(file + File.separator + buyer.getType() + "_" + UUID.randomUUID().toString().toUpperCase() + ".JPG");
        try {
            FileUtils.writeByteArrayToFile(file, Base64Util.decode(buyer.getPic().replace(StaticConfig.Base64EncodePrefix, "")));
        } catch (IOException e) {
            log.error("图片生成异常，原因是：{}", e.getMessage());
            return JsonResultUtil.resDispatcher("图片生成异常，原因是:" + e.getMessage(), 0);
        }
        String res = file.getAbsolutePath().replace(StaticConfig.WanPathReplacementPre, StaticConfig.WanUrlReplacementPre).replace("\\", "/");
        res = StaticConfig.BuyerRemoteNotifyUrlPre.replace("{TYPE}", buyer.getType()).replace("{JYLSH}", buyer.getJylsh()).replace("{DISK_PATH}", file.getAbsolutePath()).replace("{URL_WAN}", res);
        log.info("准备请求：{}", res);
        Callback callback = gson.fromJson(HttpUtil.get(res).replace("null(", "").replace(")", ""), Callback.class);
        if ("0".equalsIgnoreCase(callback.getRes())) {
            log.error("操作失败，原因是：{}", callback.getMsg());
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                log.error("上传失败的文件删除失败，原因是：{}，路径为：", e.getMessage(), file.getAbsolutePath());
            }
            return JsonResultUtil.resDispatcher(callback.getMsg(), 0);
        }
        return JsonResultUtil.resDispatcher(callback.getMsg(), 1);
    }
}
