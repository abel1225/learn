package me.abel.filePaths;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileDownload {
    private static OSSClient ossClient;

    public static void main(String[] args) throws IOException {
//        downloadFile("https://diyaimages.sharptools.cn", "/prd/2d81c0e69c56403bb5fa722337944c70.jpeg");
        String urls = "prd/73dd3e3af9b04cfca96e46e8b2de9e76.jpeg,prd/764ecad0c6514fd6b31d360bdf348567.jpeg,prd/b14bb30710b4499c9ee604a9e0432cbd.jpg,prd/5862378feeed423dba5f9e34baffaea8.jpeg,prd/f3566fa70c584d20b3b55d263032a15d.jpeg,prd/2d81c0e69c56403bb5fa722337944c70.jpeg,prd/016bb72d7d1e41a08957fd15181b8a54.jpeg,prd/860959cd6f254f7491e1f7de794d2d35.jpeg,prd/60617f3de7384e7a908ea15957fbc8a0.jpeg,prd/a1267c300ed745a39aa666dc024f98ac.jpeg,prd/808de3ec13fd47e6a3deefa43c831913.jpeg,prd/0f65be9fdf0f4eed96eaf58b48d823c5.jpeg,prd/c300bd2b09d145f0a60ec0987001b60a.jpeg,prd/6a85638717c84ee78165aa3e3a117bf7.jpeg,prd/81e2f542a44e4962b00031e722e8b34f.jpeg,prd/ef32398f1e7a43aeb00ad3d3e9320a74.jpeg,prd/e153279b28504749b10112c7d8792a8c.jpeg,prd/65bedf7dd82f4cf3a3f91121acb46138.jpeg,prd/1121802a78594d2d9e78920c2bdd8b12.jpeg,prd/ad99d0b0192a4d059735548141db5cdd.jpeg,prd/5ca1e7dc59814d4cbde898034c98a8be.jpeg";
        for (String url : urls.split(",")) {
            downloadFile("https://diyaimages.sharptools.cn/", url);
        }
    }

    public static void downloadFile(String domian, String url) throws IOException {
        OSSObject ossObject = downLoadImage(url);
        String folder = "/Users/lizhen/Desktop/files/";
        if (ossObject != null) {
            InputStream inputStream = ossObject.getObjectContent();
            FileUtils.copyToFile(inputStream, new File(folder + url));
        }
    }

    public static OSSObject downLoadImage(String fileName) {

        OSSClient ossClient = getInstance();
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest("diyaimages", fileName, HttpMethod.GET);
        // 设置过期时间。
        request.setExpiration(expiration);
        // 生成签名URL（HTTP GET请求）。
        URL signedUrl = ossClient.generatePresignedUrl(request);
        // 使用签名URL发送请求。
        Map<String, String> customHeaders = new HashMap<>();
        // 添加GetObject请求头。
        //customHeaders.put("Range", "bytes=100-1000");
        OSSObject object = ossClient.getObject(signedUrl, customHeaders);
        return object;
    }


    public static synchronized OSSClient getInstance() {
//    oss:
//    accessKey: LTAI4GBwZxA7ofEJJ82tBAd3
//    accessSecret: YlbygPm0hP3ZBqH2Flh06LGCgubrS4
//    endpoint: oss-cn-shanghai.aliyuncs.com
//    bucketName: diyaimages
//    path: liqiDev/
//    temp: liqiDev/temp/
//    url: https://diyaimages.sharptools.cn/
        if (ossClient == null) {
            synchronized (FileDownload.class) {
                if (ossClient == null) {
                    ossClient = new OSSClient("oss-cn-shanghai.aliyuncs.com", "LTAI4GBwZxA7ofEJJ82tBAd3", "YlbygPm0hP3ZBqH2Flh06LGCgubrS4");
                }
            }
        }
        return ossClient;
    }

}
