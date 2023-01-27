package com.sanhak.edss.s3;

import com.amazonaws.services.s3.transfer.TransferManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

@Transactional(readOnly = true)
@PropertySource(value = "application.properties")
@RequiredArgsConstructor
@Component
public class S3Utils {
    private final TransferManager transferManager;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void downloadFolder(String dir) throws IOException {
        try {
            File localDirectory = new File("s3-download");
            String tmp = URLDecoder.decode(dir,"utf-8");
            transferManager.downloadDirectory(bucket, tmp, localDirectory);
        } catch (IOException e) {
            System.out.println("ERR");
            e.getMessage();
        }
    }
}
