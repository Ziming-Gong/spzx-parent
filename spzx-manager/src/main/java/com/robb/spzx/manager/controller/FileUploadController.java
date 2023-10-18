package com.robb.spzx.manager.controller;

import com.robb.spzx.manager.service.FileUploadService;
import com.robb.spzx.manager.service.SysUserService;
import com.robb.spzx.model.vo.common.Result;
import com.robb.spzx.model.vo.common.ResultCodeEnum;
import io.minio.messages.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;


    /**
     * <input type="file" name="file"> file要对应下面的file
     *
     * @param file
     * @return
     */
    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestParam("file") MultipartFile file) {
        //1. 得到上传的文件

        //2.调用service里面的方法上传 的到minio路径上传
        String url = fileUploadService.upload(file);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }
}
