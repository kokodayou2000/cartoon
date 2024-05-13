package org.example.service.impl;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import io.minio.ObjectStat;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.example.config.MinioConfig;
import org.example.service.IFileService;
import org.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
@Service
@Slf4j
public class FileServiceImpl implements IFileService {


//    @Autowired
//    private MinioConfig minioConfig;
//
//    @Resource
//    private MinioService minioService;
//
//    @SneakyThrows
//    @Override
//    public String uploadToMinio(InputStream is,String contentType,String name) {
//
//        // 获取扩展名
//        String extension = "pdf";
//        // 随机生成文件名
//        String randomName = CommonUtil.getRandomFileName();
//        String randomFileName = randomName + "."+extension;
//
//        // 转换成path
//        Path path = Path.of(randomFileName);
//        String baseUrl = minioConfig.getUrl()+minioConfig.getBucket()+"/";
//        HashMap<String, String> header = new HashMap<>();
//        header.put("response-content-type", "application/pdf");
//
//        try {
//
//            minioService.upload(path, is, ContentType.create("application/pdf"),header);
//            ObjectStat metadata = minioService.getMetadata(path);
//            String url =  baseUrl + metadata.name();
//            log.info("Url {}",url);
//            return url;
//        } catch (MinioException ex) {
//            throw new IllegalStateException(ex.getMessage());
//        }
//    }
}
