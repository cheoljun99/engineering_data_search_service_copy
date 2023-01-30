package com.sanhak.edss.cad;

import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.sanhak.edss.aspose.AsposeUtils;
import com.sanhak.edss.s3.S3Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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


            String existDir = AsposeUtils.dataDir+mainCategory[3];
            //System.out.println(existDir);
            /*File checkfile = new File(existDir);
            if(!checkfile.exists()){
                wait(1000);
            }*/

            /*DelayThread thread1 = new DelayThread(s3Utils);
            thread1.setName(mainCategory[3]);
            thread1.start();
            thread1.join();*/
            /*final Object lock = new Object();
            synchronized (lock){
                lock.wait();
                File checkfile = new File(existDir);
                if(checkfile.exists()){
                    notifyAll();
                }

            }*/
            Map<String, String[]> fileInfo = asposeUtils.searchCadFleInDataDir(mainCategory[3]);

            System.out.println("cadServiceimpl222");
            for (Map.Entry<String, String[]> entry: fileInfo.entrySet()) {
                Cad cad = new Cad(mainCategory[3], entry.getValue()[0], entry.getKey(), entry.getValue()[1], entry.getValue()[2]);
                cadRepository.save(cad);
            }
            remove(new File(AsposeUtils.dataDir));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void remove(File file) throws IOException {
        if (file.isDirectory()) {
            removeDirectory(file);
        } else {
            file.delete();
        }
    }
    public void removeDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }
        directory.delete();
    }



    public List<Cad> searchCadFile(String searchText) {
        try {
            if (searchText == "")
                return null;
            String[] text = searchText.split("\"");
            String[] eachText = text[3].split(" ");
            List<Cad> result = cadRepository.findAllByTitleContains(eachText[0]);
            result = Stream.concat(result.stream(), cadRepository.findAllByIndexContains(eachText[0]).stream()).distinct().toList();
            for (int i=1; i < eachText.length; i++) {
                result = Stream.concat(result.stream(), cadRepository.findAllByTitleContains(eachText[i]).stream()).distinct().toList();
                result = Stream.concat(result.stream(), cadRepository.findAllByIndexContains(eachText[i]).stream()).distinct().toList();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
