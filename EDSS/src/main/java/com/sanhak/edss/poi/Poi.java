/*package com.sanhak.edss.poi;

import com.sanhak.edss.aspose.ExtractCadText;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.sanhak.edss.cad.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class Poi {
    private static List<Cad> findFileInfo() {
        System.out.println(ExtractCadText.getDataDir());
        List<Cad> CadData = new ArrayList<>();
        try {
            Set<String> directories = Stream.of(new File(ExtractCadText.getDataDir()).listFiles())
                    .filter(File::isDirectory)
                    .map(File::getName)
                    .collect(Collectors.toSet());
            for (String directory: directories) {
                Map<String, String> fileInfo = ExtractCadText.searchCadFleInDataDir(directory);
                for (Map.Entry<String, String> entry: fileInfo.entrySet()) {
                    Cad dwgFile = new Cad(directory, directory, entry.getKey(), entry.getValue());
                    CadData.add(dwgFile);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return CadData;
    }

    static String filePath = "C:\\java\\backend\\engineering_data_search_service\\EDSS\\src\\main\\resources\\static";
    static String fileName = "test.xlsx";
    public static void main(String[] args) {

        String a = "가나다라마바사";
        System.out.println(a.substring(0,3));
        System.out.println(a.substring(3));
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("engineerging data");
        Map<String, Object[]> data = new TreeMap<>();
        List<Cad> dwgFiles = findFileInfo();

        int tmp = 1, rowNum = 0, columnNum;

        data.put("1", new Object[] {"ID", "대분류", "소분류", "제목", "인덱스"});
        for (Cad dwgFile: dwgFiles) {
            data.put(String.valueOf(++tmp), new Object[] {dwgFile.getId(), dwgFile.getMainCategory(),
                    dwgFile.getSubCategory(), dwgFile.getTitle(), dwgFile.getIndex()});
        }
        Set<String> keyset = data.keySet();

        for (String key: keyset) {
            Row row = sheet.createRow(rowNum++);
            Object[] objArr = data.get(key);
            columnNum = 0;
            for (Object obj: objArr) {
                Cell column = row.createCell(columnNum++);
                if (obj instanceof String) {
                    while(((String)obj).length()>32767) {
                        Object obj_tmp = obj;
                        obj = ((String) obj).substring(0, 32766);
                        obj_tmp = ((String) obj_tmp).substring(32766);
                        column.setCellValue((String) obj);
                        column = row.createCell(columnNum++);
                        obj = obj_tmp;

                    }
                    column.setCellValue((String)obj);
                } else if (obj instanceof Integer) {
                    column.setCellValue((Integer)obj);
                }
            }
        }

        try (FileOutputStream out = new FileOutputStream(new File(filePath, fileName))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/
