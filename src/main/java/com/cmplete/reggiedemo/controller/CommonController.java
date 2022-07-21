package com.cmplete.reggiedemo.controller;

import com.cmplete.reggiedemo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String base;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        /*file是临时文件,需要转存到指定位置,否则本次完成后临时文件就会删除*/
        log.info(file.toString());
        /*传输*/
        try {
            file.transferTo(new File(base+file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(file.getOriginalFilename());
    }
    @GetMapping("/download")
    /*文件下载*/
    public void download(String name, HttpServletResponse response) throws IOException {
        //输入流读取文件
        FileInputStream fileInputStream=new FileInputStream(new File(base+name));
        ServletOutputStream stream = response.getOutputStream();
        byte[] bytes=new byte[1024];
        //输出流,写回浏览器,在浏览器进行展示
        int len=0;
        response.setContentType("image/jpeg");
        while ((len=fileInputStream.read(bytes))!=-1){
            stream.write(bytes,0,len);
            stream.flush();
        }
        stream.close();
        fileInputStream.close();
    }
}
