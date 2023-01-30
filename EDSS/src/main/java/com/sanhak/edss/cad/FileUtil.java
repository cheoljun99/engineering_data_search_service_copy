/*package com.sanhak.edss.cad;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class FileUtil {
    public static void remove(File file) throws IOException {
        if (file.isDirectory()) {
            removeDirectory(file);
        } else {
            file.delete();
        }
    }

    public static void removeDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }
        directory.delete();
    }
}
*/