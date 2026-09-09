package myshop.shop.service.FileService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    /**
     * 파일명 생성
     */
    public String createStoreName(String fileName);


    /**
     * 파일 저장
     * @return storeFileName
     */
    public String storeFile(MultipartFile multipartFile) throws IOException;


    /**
     * 파일 여러개 저장
     * @return List<storeFileName>
     */
    public List<String> storeFiles(List<MultipartFile> multipartFileList) throws IOException;


    /**
     * 파일 삭제
     */
    public void removeFile(String fileDir);

}
