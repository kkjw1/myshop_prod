package myshop.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    @Value("${file.path}")
    private String fileDir;
    @Value("${file.exteral-path}")
    private String exteralFileDir;

    /**
     * 파일명 생성
     */
    public String createStoreName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos);
        String uuid = UUID.randomUUID().toString();
        return uuid + ext;
    }



    /**
     * 파일 저장
     * @return storeFileName
     */
    public String storeFile(MultipartFile multipartFile) throws IOException {
        String storeFileName = null;
        if (!multipartFile.isEmpty()) {
            String storeName = createStoreName(multipartFile.getOriginalFilename());
            storeFileName = fileDir + storeName;
            log.info("파일 저장: {}", exteralFileDir + storeName);
            multipartFile.transferTo(new File(exteralFileDir + storeName));
        }
        return storeFileName;
    }



    /**
     * 파일 여러개 저장
     * @return List<storeFileName>
     */
    public List<String> storeFiles(List<MultipartFile> multipartFileList) throws IOException {
        List<String> storeFileNameList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            if (!multipartFile.isEmpty()) {
                storeFileNameList.add(storeFile(multipartFile));
            }
        }
        return storeFileNameList;
    }



    /**
     * 파일 삭제
     */
    public void removeFile(String fileDir) {
        String realPath = fileDir.replace(this.fileDir, exteralFileDir);
        log.info("removeFile Path={}",realPath);
        File file = new File(realPath);

        if (file.exists()) {
            if (file.delete()) {
                log.info("파일 삭제 성공: {}", realPath);
            } else {
                log.info("파일 삭제 실패 (권한 문제 등)");
            }
        } else {
            log.info("파일을 찾을 수 없습니다: {}", realPath);
        }
    }
}
