package org.example.service.impl;


import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.example.MinioService;
import org.example.config.MinioConfig;
import org.example.model.ImageDO;
import org.example.repository.ImageRepository;
import org.example.service.IFileService;
import org.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

@Service
@Slf4j
public class FileServiceImpl implements IFileService {


    private final MinioConfig minioConfig;

    private final ImageRepository imageRepository;

    @Resource
    private MinioService minioService;


    @Autowired
    public FileServiceImpl(MinioConfig minioConfig,ImageRepository imageRepository){
        this.minioConfig = minioConfig;
        this.imageRepository = imageRepository;
    }

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

            String url =  baseUrl + path;
            imageDO.setUrl(url);
            imageDO.setId(CommonUtil.generateUUID());
            return imageRepository.save(imageDO);

        } catch (IOException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    @Override
    public ImageDO uploadToAliyun(MultipartFile file) {
        return null;
    }
}
