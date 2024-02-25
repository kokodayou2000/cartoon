package org.example.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import io.minio.ObjectStat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.example.config.MinioConfig;
import org.example.config.OSSConfig;
import org.example.constant.TimeConstants;
import org.example.service.FileService;
import org.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@Slf4j
public class FileServiceImpl implements FileService {


    @Autowired
    private OSSConfig ossConfig;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioService minioService;

    @Override
    public String uploadUserImg(MultipartFile file) {

        //建立归档
        String bucketName = ossConfig.getBucketName();
        String endPoint = ossConfig.getEndPoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();

        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TimeConstants.TIME_FORMAT);

        //按照时间对文件进行归档
        String folder = dtf.format(now);

        //自动生成32uuid作为文件名
        String fileName = CommonUtil.generateUUID();
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //获取扩展名
        assert originalFilename != null;
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        // OSS会自动创建对应的文件
        String newFileName = folder+"/"+fileName+extension;
        PutObjectResult putObjectResult = null;
        String imgUrl = null;
        try {
            putObjectResult = ossClient.putObject(bucketName, newFileName, file.getInputStream());
            if (putObjectResult != null){
                //log.info("response uri = "+putObjectResult.getResponse().getUri());
                imgUrl = "https://"+bucketName+"."+endPoint+"/"+newFileName;
                //log.info("imgUrl = "+imgUrl);


            }
        } catch (IOException e) {
            log.error("文件上传失败{}",e.getMessage());
            throw new RuntimeException(e);
        }finally {

            ossClient.shutdown();
        }

        return imgUrl;
    }



    @Override
    public String uploadToMinio(MultipartFile file) {
        // 获取文件名
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        // 获取扩展名
        String extension = FilenameUtils.getExtension(fileName);
        // 随机生成文件名
        String randomName = CommonUtil.getRandomFileName();
        String randomFileName = randomName + "."+extension;

        // 转换成path
        Path path = Path.of(randomFileName);
        String baseUrl = minioConfig.getUrl()+minioConfig.getBucket()+"/";
        try {
            minioService.upload(path, file.getInputStream(), file.getContentType());
            ObjectStat metadata = minioService.getMetadata(path);
            log.info("this file {} storage in bucket: {} on date: {}", metadata.name(), metadata.bucketName(), metadata.createdTime());
            return baseUrl + metadata.name();
        } catch (IOException | MinioException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }
}
