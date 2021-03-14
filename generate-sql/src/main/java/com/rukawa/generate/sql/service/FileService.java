package com.rukawa.generate.sql.service;

import com.rukawa.common.util.BeanUtil;
import com.rukawa.common.util.FileUtil;
import com.rukawa.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Slf4j
@Service
public class FileService {

    @Value("${image.location}")
    private String imageLocation;

    private String serverPath = System.getProperty("user.dir");

    public boolean uploadFile(List<MultipartFile> files) {
        String serverImagePath = serverPath + File.separator + getImageLocation() + File.separator;
        int count = 0;
        for (MultipartFile file : files) {
            String relFile = serverImagePath + StringUtil.generateUniqueCode() + file.getOriginalFilename();
            try {
                count += FileUtil.transferFile((FileInputStream) file.getInputStream(), relFile) ? 1 : 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count == files.size();
    }

    public String uploadFile(MultipartFile file) {
        String serverImagePath = serverPath + File.separator + getImageLocation() + File.separator;
        String relFile = serverImagePath + StringUtil.generateUniqueCode()
                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        try {
            if (FileUtil.transferFile((FileInputStream) file.getInputStream(), relFile)) {
                return File.separator + relFile.replace(serverPath + File.separator, "");
            }
        } catch (IOException e) {
            log.error("#uploadFile(file) occur: ", e);
        }
        return "";
    }

    public String transferFile(InputStream inputStream, String fileSuffix) {
        String relFileName = StringUtil.generateUniqueCode() + "." + fileSuffix;
        String relFile = serverPath + File.separator + getImageLocation() + File.separator + relFileName;
        byte[] buff = new byte[2048];
        try (OutputStream outputStream = new FileOutputStream(relFile)) {
            int len = -1;
            while ((len = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
            }
            outputStream.flush();
            return relFileName;
        } catch (IOException e) {
            log.error("#transferFile(inputStream, fileSuffix) occur: ", e);
        } finally {
            if (!BeanUtil.isEmpty(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("#transferFile(inputStream, fileSuffix) occur: ", e);
                }
            }
        }
        return "";
    }

    private String getImageLocation() {
        if (BeanUtil.isEmpty(imageLocation)) {
            return "";
        }
        return imageLocation.indexOf(":") == -1 ? imageLocation.replaceAll("/", "") : imageLocation.substring(imageLocation.indexOf(":") + 1).replaceAll("/", "");
    }

}
