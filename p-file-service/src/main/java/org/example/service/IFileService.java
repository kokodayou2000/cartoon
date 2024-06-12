package org.example.service;

import org.example.model.ImageDO;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    ImageDO uploadToMinio(MultipartFile file);

    ImageDO uploadToAliyun(MultipartFile file);
}
