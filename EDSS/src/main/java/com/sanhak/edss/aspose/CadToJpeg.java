//package com.sanhak.edss.aspose;
//
//import com.aspose.cad.Image;
//import com.aspose.cad.imageoptions.JpegOptions;
//import java.io.ByteArrayOutputStream;
//
//public class CadToJpeg {
//    static final String dir = "C:\\springtest\\demo\\src\\main\\resources\\static";
//    public static void main(String[] args) {
//
//
//        try {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//            Image cadImage = Image.load("C:\\java\\backend\\engineering_data_search_service\\EDSS\\src\\main\\resources\\DWGDrawings\\000. T4000001-001 종합무선중계설비 속지 - 복사본.dwg");
//            System.out.println("11111");
//            //cadImage.save("C:\\springtest\\demo\\src\\main\\resources\\static\\output");
//            cadImage.save(stream, new JpegOptions());
//            cadImage
//
//            //cadImage.save("C:\\springtest\\demo\\src\\main\\resources\\static\\output1.bmp", new BmpOptions());
//            //cadImage.save("C:\\springtest\\demo\\src\\main\\resources\\static\\output2.png", new PngOptions());
//            //cadImage.save("C:\\springtest\\demo\\src\\main\\resources\\static\\output3.tif", new TiffOptions(TiffExpectedFormat.Default));
//            System.out.println("22222");
//        }catch(Exception e){
//            System.out.println("error");
//        }
//    }
//}
