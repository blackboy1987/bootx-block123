package com.bootx.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;

/**
 * @author black
 */
public final class UploadUtils {
    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static final String accessKeyId = "LTAI4GJqk6kDCvgv2gJDhdAc";
    private static final String accessKeySecret = "0nQLRyTzSnC81zyHnPmjtMEIUoFbne";
    private static final String bucketName = "bootx-jlb";

    public static void upload(String url,String path){
        if(StringUtils.startsWith(url,"http")||StringUtils.startsWith(url,"https")){

        }else{
            url="http:"+url;
        }
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            InputStream inputStream = new URL(url).openStream();
            ossClient.putObject(bucketName, path, inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }
    }

    public static void upload(String path, File file, String contentType){
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            objectMetadata.setContentLength(file.length());
            ossClient.putObject(bucketName, StringUtils.removeStart(path, "/"), inputStream, objectMetadata);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }


    public static String getUrl(String path){
        return "https://bootx-jlb.oss-cn-hangzhou.aliyuncs.com/"+path;
    }


    public static void main(String[] args) {
        String fileType = "image";
        String path = fileType+"/a/d/1.jpg";
        upload("http://img14.360buyimg.com/n1/s800x800_jfs/t1/140681/7/19555/100905/5fe0b4ccEf9fcf3a8/fe901e1d32702b55.jpg",path);
        String url = "https://bootx-pdd.oss-cn-hangzhou.aliyuncs.com/"+path;
    }
}
