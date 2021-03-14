package com.rukawa.generate.sql.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class Base64Service {

    public InputStream parseBase64ToStream(String base64) {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            int offset = base64.indexOf(",") + 1;
            byte[] bytes = base64Decoder.decodeBuffer(base64.substring(offset));
            return new ByteArrayInputStream(bytes);
        } catch (IOException e) {
            log.error("#parseBase64ToStream(base64) occur: ", e);
        }
        return null;
    }

}
