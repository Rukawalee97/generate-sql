package com.rukawa.generate.sql.controller;

import com.rukawa.common.util.CollectionUtil;
import com.rukawa.common.util.StringUtil;
import com.rukawa.generate.sql.dto.Result;
import com.rukawa.generate.sql.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result uploadFile(List<MultipartFile> files) {
        Result result = null;
        if (!CollectionUtil.isEmpty(files)) {
            if (files.size() == 1) {
                String resultUrl = fileService.uploadFile(files.get(0));
                if (StringUtil.isEmpty(resultUrl)) {
                    result = Result.fail(false);
                    result.setMessage("文件上传失败!");
                } else {
                    result = Result.success(resultUrl);
                    result.setMessage("文件上传成功!");
                }
            } else {
                if (fileService.uploadFile(files)) {
                    result = Result.success(true);
                    result.setMessage("文件上传成功!");
                } else {
                    result = Result.fail(false);
                    result.setMessage("文件上传失败!");
                }
            }
        }
        return result;
    }

}
