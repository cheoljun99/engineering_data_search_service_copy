package com.sanhak.edss.s3;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.core.io.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;
    @GetMapping(value = "/download/folder/**", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFolder(HttpServletRequest request) throws IOException, InterruptedException{
        String prefix = getPrefix(request.getRequestURI(), "/s3/download/folder/");
        String tmp = URLDecoder.decode(prefix,"utf-8");

        System.out.println(prefix);
        System.out.println(tmp);

        Resource resource = s3Service.downloadFolder(tmp);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + new String(resource.getFilename().getBytes("UTF-8"), "ISO-8859-1"));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    private String getPrefix(String uri, String regex){
        String[] split = uri.split(regex);
        return split.length < 2 ? "" : split[1];
    }

}
