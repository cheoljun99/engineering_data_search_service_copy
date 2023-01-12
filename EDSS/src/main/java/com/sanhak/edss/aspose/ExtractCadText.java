package com.sanhak.edss.aspose;

import com.aspose.cad.fileformats.cad.CadImage;
import com.aspose.cad.fileformats.cad.cadconsts.CadEntityTypeName;
import com.aspose.cad.fileformats.cad.cadobjects.CadBaseEntity;
import com.aspose.cad.fileformats.cad.cadobjects.CadBlockEntity;
import com.aspose.cad.fileformats.cad.cadobjects.CadText;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class ExtractCadText {
    private static final String dataDir = setDataPath() + "DWGDrawings" + File.separator;

    public static String setDataPath() {
        File dir = new File(System.getProperty("user.dir"));
        dir = new File(dir, "src");
        dir = new File(dir, "main");
        dir = new File(dir, "resources");
        return dir + File.separator;
    }

    public static String getDataDir() {
        return dataDir;
    }

    public static Map<String, String> searchCadFleInDataDir(String dirName) {
        Map<String, String> fileInfo = new HashMap<>();
        try {
            Files.walkFileTree(Paths.get(dataDir + dirName), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (!Files.isDirectory(file) && file.getFileName().toString().contains(".dwg")) {
                        String fileName = file.getFileName().toString();
                        String fileIndex = extractTextInCadFile(file.toAbsolutePath().toString());
                        fileInfo.put(fileName, fileIndex);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    public static String extractTextInCadFile(String fileName) {
        String index = "";

        // Load an existing DWG file as CadImage.
        CadImage cadImage = (CadImage) CadImage.load(fileName);

        // Search for text in the block section
        for (CadBlockEntity blockEntity : cadImage.getBlockEntities().getValues()) {
            for (CadBaseEntity entity : blockEntity.getEntities()) {
                if (entity.getTypeName() == CadEntityTypeName.TEXT) {
                    CadText childObjectText = (CadText)entity;
                    index = index + childObjectText.getDefaultValue() + ", ";
                }
            }
        }
        return index;
    }
}
