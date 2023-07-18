package com.github.shiayanga.rest.controller;

import com.github.shiayanga.qrcode.QrCodeHelper;
import com.google.zxing.WriterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LiYang
 */
@RestController
@RequestMapping("/qrcode")
public class QrCodeController {
    @GetMapping("/generate")
    public void generate(HttpServletResponse response) throws IOException, WriterException {
        ServletOutputStream outputStream = response.getOutputStream();
        QrCodeHelper.generate(outputStream);
    }
}
