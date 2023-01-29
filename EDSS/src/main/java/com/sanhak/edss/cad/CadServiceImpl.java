package com.sanhak.edss.cad;

import com.sanhak.edss.aspose.AsposeUtils;
import com.sanhak.edss.s3.S3Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class CadServiceImpl implements CadService {
    private final CadRepository cadRepository;
    private final S3Utils s3Utils;
    private final AsposeUtils asposeUtils;

    public void saveCadFile(String dir) {
        try {
            System.out.println("cadServiceimpl");
            String[] mainCategory = dir.split("\"");
            s3Utils.downloadFolder(mainCategory[3]);
            Map<String, String[]> fileInfo = asposeUtils.searchCadFleInDataDir(mainCategory[3]);
            System.out.println("cadServiceimpl222");
            for (Map.Entry<String, String[]> entry: fileInfo.entrySet()) {
                Cad cad = new Cad(mainCategory[3], entry.getValue()[0], entry.getKey(), entry.getValue()[1], entry.getValue()[2]);
                cadRepository.save(cad);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cad> searchCadFile(String searchText) {
        try {
            String[] text = searchText.split("\"");
            String[] eachText = text[3].split(" ");
            List<Cad> result = null;
            for (String s: eachText) {
                List<Cad> resultByTitle = cadRepository.findAllByTitleContains(s);
                List<Cad> resultByIndex = cadRepository.findAllByIndexContains(s);
                result = Stream.concat(resultByTitle.stream(), resultByIndex.stream()).distinct().toList();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
