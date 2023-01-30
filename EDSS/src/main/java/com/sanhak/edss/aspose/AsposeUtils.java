package com.sanhak.edss.aspose;

import com.aspose.cad.Image;
import com.aspose.cad.fileformats.cad.CadImage;
import com.aspose.cad.fileformats.cad.cadconsts.CadEntityTypeName;
import com.aspose.cad.fileformats.cad.cadobjects.CadBaseEntity;
import com.aspose.cad.fileformats.cad.cadobjects.CadBlockEntity;
import com.aspose.cad.fileformats.cad.cadobjects.CadMText;
import com.aspose.cad.fileformats.cad.cadobjects.CadText;
import com.aspose.cad.imageoptions.BmpOptions;
import com.aspose.cad.imageoptions.CadRasterizationOptions;
import com.aspose.cad.imageoptions.JpegOptions;
import com.aspose.cad.imageoptions.PngOptions;
import com.sanhak.edss.s3.S3Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AsposeUtils {
    private final S3Utils s3Utils;
    private static final String dataDir = setDataPath() + "s3-download" + File.separator;
    public static final String ImagePath = setDataPath() + "testtest" + File.separator;

    public static String setDataPath() {
        File dir = new File(System.getProperty("user.dir"));
//        dir = new File(dir, "EDSS");//temp
        return dir + File.separator;
    }

    public Map<String, String[]> searchCadFleInDataDir(String dir) {
        System.out.println("searchCadFileInDataDir");
        System.out.println(dir);
        Map<String, String[]> fileInfo = new HashMap<>();
        try {
            System.out.println("searchCadFileInDataDir2222");
            Files.walkFileTree(Paths.get(dataDir + dir), new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
                    System.out.println("visitFile");
                    if (!Files.isDirectory(file) && file.getFileName().toString().contains(".dwg")) {
                        String fileName = file.getFileName().toString();
                        String filePath = file.toAbsolutePath().toString();
                        String fileIndex = extractTextInCadFile(filePath);
                        ByteArrayOutputStream bos = CadToJpeg(filePath);
                        String S3url = s3Utils.putS3("image/", fileName, bos);
                        System.out.println(fileName.indexOf(fileName));
                        filePath = filePath.substring(filePath.indexOf(dir), filePath.indexOf(fileName));
                        fileInfo.put(fileName, new String[]{filePath, fileIndex, S3url});
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println("visitFIle error");
            e.printStackTrace();
        }
        return fileInfo;
    }

    public static String extractTextInCadFile(String fileName) {

        System.out.println("extractTextInCadFile");

        String index = "";

        // Load an existing DWG file as CadImage.
        CadImage cadImage = (CadImage) CadImage.load(fileName);

        // Search for text in the block section
        for (CadBlockEntity blockEntity : cadImage.getBlockEntities().getValues()) {
            for (CadBaseEntity entity : blockEntity.getEntities()) {
                if (entity.getTypeName() == CadEntityTypeName.TEXT) {
                    CadText childObjectText = (CadText)entity;
                    index = index + childObjectText.getDefaultValue() + "| ";
                }

                else if (entity.getTypeName() == CadEntityTypeName.MTEXT) {
                    CadMText childObjectText = (CadMText)entity;
                    index += childObjectText.getText();
                    index += "| ";
                }

            }
        }
        return index;
    }

    public ByteArrayOutputStream CadToJpeg(String filePath) {
        System.out.println("CadToJpeg");


        Image cadImage = Image.load(filePath);

        CadRasterizationOptions rasterizationOptions = new CadRasterizationOptions();
        rasterizationOptions.setPageHeight(200);
        rasterizationOptions.setPageWidth(200);

        JpegOptions options = new JpegOptions();

        options.setVectorRasterizationOptions(rasterizationOptions);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        cadImage.save(bos, options);
        return bos;

    }
}
