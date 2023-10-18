package com.robb.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.robb.spzx.common.exception.CustomException;
import com.robb.spzx.manager.properties.MinioProperties;
import com.robb.spzx.manager.service.FileUploadService;
import com.robb.spzx.model.vo.common.ResultCodeEnum;
import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {


    @Autowired
    private MinioProperties minioProperties;


    /**
     * 优化：
     * 1 用properties替代
     * 2 文件名优化
     *
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioProperties.getEndpointUrl())
                            .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                            .build();

            // Make 'spzx-bucket' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {
                System.out.println("Bucket " + minioProperties.getBucketName() + " already exists.");
            }

            //获取上传的文件名称  // 20231017/(uuid) + 01.jpg
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String originalFilename = dateDir + "/" + uuid + file.getOriginalFilename();


            //得到文件的输入流
            InputStream inputStream = file.getInputStream();

            // Upload
            // 'spzx-bucket'.
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucketName())
                            .object(originalFilename)
                            .stream(inputStream, file.getSize(), -1)
                            .build());

            //获取上传文件在minio的路径
            // http://127.0.0.1:9000/spzx-bucket/screenshot.jpeg
            String url = minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + originalFilename;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
