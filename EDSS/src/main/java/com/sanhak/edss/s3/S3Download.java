//package com.sanhak.edss.s3;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Optional;
//import java.util.UUID;
////https://blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=mankeys&logNo=220902829874&categoryNo=0&parentCategoryNo=0&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postList
////https://dblog94.tistory.com/entry/Spring-AWS-S3%EB%A5%BC-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class S3Download {
//    private final AmazonS3Client amazonS3Client;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    public String upload(MultipartFile file, String dirName) throws IOException{
//        File uploadFile = convert(file).orElseThrow(() -> new IllegalArgumentException("file 전달에 실패했습니다."));
//        File news = new File(System.getProperty("user.dir")+"/"+ UUID.randomUUID()+".jpg");
//        uploadFile.renameTo(news);
//        return upload(news, dirName);
//    }
//
//    public String upload(File uploadFile, String dirName){
//        String fileName = dirName + "/" + uploadFile.getName();
//        String uploadImageURL = putS3(uploadFile, fileName);
//        removeNewFile(uploadFile);
//        return uploadImageURL;
//    }
//
//    private String putS3(File uploadFile, String fileName){
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//    }
//
//    private String getS3(){
//        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucket,))
//    }
//
//
//    public HttpStatusCode deleteFile(String fileName){
//        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, "static/"+fileName);
//        amazonS3Client.deleteObject(deleteObjectRequest);
//        return HttpStatus.ACCEPTED;
//    }
//
//    private void removeNewFile(File targetFile){
//        if(targetFile.delete()){
//            log.info("파일 삭제 완료");
//        }
//        else{
//            log.info("파일 삭제 실패");
//        }
//    }
//
//    private Optional<File> convert(MultipartFile file) throws IOException{
//        File convertFile = new File(System.getProperty("user.dir")+"/"+file.getOriginalFilename());
//        if(convertFile.createNewFile()){
//            try(FileOutputStream fos = new FileOutputStream(convertFile)){
//                fos.write(file.getBytes());
//            }
//            return Optional.of(convertFile);
//        }
//        return Optional.empty();
//    }
//
//}
