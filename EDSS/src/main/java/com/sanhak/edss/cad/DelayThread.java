/*package com.sanhak.edss.cad;

import com.sanhak.edss.s3.S3Utils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DelayThread extends Thread{

    private final S3Utils s3Utils;

    @Override
    public void run(){
        try{
            String str = DelayThread.currentThread().getName();
            System.out.println(str);
            s3Utils.downloadFolder(str);

        }catch (Exception e){
            System.out.println("DelayThread error");
        }
    }
}*/
