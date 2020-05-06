package com.yjq.fileupload.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.element.VariableElement;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class FileUploadController {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("/yyyy/MM/dd");
    @PostMapping("/upload")
    public String upload(MultipartFile file, HttpServletRequest request){
        String format = simpleDateFormat.format(new Date());
        String realPath = request.getServletContext().getRealPath("/img") + format;
        File folder = new File(realPath);
        if (!folder.exists()){
            folder.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        try {
            file.transferTo(new File(folder, newName));
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/img" + format +"/"+ newName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
    @PostMapping("/uploads")
    public String uploads(MultipartFile[] files, HttpServletRequest request){
        String format = simpleDateFormat.format(new Date());
        String realPath = request.getServletContext().getRealPath("/img") + format;
        File folder = new File(realPath);
        if (!folder.exists()){
            folder.mkdirs();
        }
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String newName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
            try {
                file.transferTo(new File(folder, newName));
                String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/img" + format +"/"+ newName;
                System.out.println(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }
}
