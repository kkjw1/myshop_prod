package myshop.shop.service.FileService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileService {
    /**
     * 파일명 생성
     */
    public String createStoreName(String fileName);


    /**
     * 파일 저장
     * @return storeFileName
     */
    public Map<String, String> storeFile(MultipartFile multipartFile) throws IOException;


    /**
     * 파일 여러개 저장
     * @return List<storeFileName>
     */
    public List<Map<String, String>> storeFiles(List<MultipartFile> multipartFileList) throws IOException;


    /**
     * 파일 삭제
     */
    public void removeFile(String imageUrl, String imageName);

}
