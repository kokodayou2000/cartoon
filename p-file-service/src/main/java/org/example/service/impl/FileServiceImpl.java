package org.example.service.impl;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import io.minio.ObjectStat;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.example.config.MinioConfig;
import org.example.model.ImageDO;
import org.example.repository.ImageRepository;
import org.example.service.IFileService;
import org.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

@Service
@Slf4j
public class FileServiceImpl implements IFileService {


    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioService minioService;

    @Autowired
    private ImageRepository imageRepository;


    @SneakyThrows
    @Override
    public ImageDO uploadToMinio(MultipartFile file) {
        ImageDO imageDO = new ImageDO();

        // 获取文件名
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        // 获取扩展名
        String extension = FilenameUtils.getExtension(fileName);
        // 随机生成文件名
        String randomName = CommonUtil.getRandomFileName();
        String randomFileName = randomName + "."+extension;
        BufferedImage image = ImageIO.read(file.getInputStream());

        int height = image.getHeight();
        int width = image.getWidth();

        imageDO.setWidth(width);
        imageDO.setHeight(height);

        // 转换成path
        Path path = Path.of(randomFileName);
        String baseUrl = minioConfig.getUrl()+minioConfig.getBucket()+"/";
        try {
            minioService.upload(path, file.getInputStream(), file.getContentType());
            ObjectStat metadata = minioService.getMetadata(path);
            String url =  baseUrl + metadata.name();
            imageDO.setUrl(url);
            imageDO.setId(CommonUtil.generateUUID());
            return imageRepository.save(imageDO);

        } catch (IOException | MinioException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }
}
