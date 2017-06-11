package xin.fallen.ProjUsedVeh.config;

import xin.fallen.ProjUsedVeh.annotation.Alias;

public class StaticConfig {
    @Alias("wan_dir_path_new")
    public static String WanDirPathNew;
    @Alias("wan_path_replacement_new")
    public static String WanPathReplacementNew;
    @Alias("wan_url_replacement_new")
    public static String WanUrlReplacementNew;
    @Alias("base64_encode_prefix")
    public static String Base64EncodePrefix;
    @Alias("remote_notify_url")
    public static String RemoteNotifyUrl;
    @Alias("seller_remote_notify_url")
    public static String SellerRemoteNotifyUrlPre;
    @Alias("buyer_remote_notify_url")
    public static String BuyerRemoteNotifyUrlPre;
    @Alias("wan_dir_path_pre")
    public static String WanDirPathPre;
    @Alias("wan_path_replacement_pre")
    public static String WanPathReplacementPre;
    @Alias("wan_url_replacement_pre")
    public static String WanUrlReplacementPre;
    @Alias("file_upload_path")
    public static String FileUploadPath;
    @Alias("file_path_replacement")
    public static String FilePathReplacement;
    @Alias("file_url_pre")
    public static String FileUrlPre;
    //    ftp配置
    @Alias("ftp_default_pool_size")
    public static int DefaultPoolSize;
    @Alias("ftp_upload_path")
    public static String FtpUploadPath;
    @Alias("ftp_filename_pattern")
    public static String FtpFilenamePattern;
}