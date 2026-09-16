package myshop.shop.service.FileService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

//@Service
@Slf4j
public class LocalFileService implements FileService {

    @Value("${file.path}")
    private String fileDir;
    @Value("${file.exteral-path}")
    private String exteralFileDir;

    /**
     * 파일명 생성
     */
    @Override
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
    @Override
    public Map<String, String> storeFile(MultipartFile multipartFile) throws IOException {
        String storeFileName = null;
        Map<String, String> result = new HashMap<>();
        if (!multipartFile.isEmpty()) {
            String storeName = createStoreName(multipartFile.getOriginalFilename());
            storeFileName = fileDir + storeName;
            log.info("파일 저장: {}", exteralFileDir + storeName);
            multipartFile.transferTo(new File(exteralFileDir + storeName));

            result.put("imageUrl", storeFileName);
            result.put("imageName", storeName);
        }

        return result;
    }



    /**
     * 파일 여러개 저장
     * @return List<storeFileName>
     */
    @Override
    public List<Map<String, String>> storeFiles(List<MultipartFile> multipartFileList) throws IOException {
        List<Map<String, String>> storeFileNameList = new ArrayList<>();

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
    @Override
    public void removeFile(String imageUrl, String imageName) {
        String realPath = imageUrl.replace(this.fileDir, exteralFileDir);
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
