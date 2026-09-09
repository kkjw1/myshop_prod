package myshop.shop.service.FileService;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.ConfigFileReader.ConfigFile;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.transfer.UploadConfiguration;
import com.oracle.bmc.objectstorage.transfer.UploadManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class OracleFileService implements FileService {

    @Value("${file.path}")
    private String fileDir;
    @Value("${file.exteral-path}")
    private String exteralFileDir;

    @Value("${file.oracle.bucket_name}")
    private String bucketName;
    @Value("${file.oracle.bucket_name_space}")
    private String bucketNameSpace;
    @Value("${file.oracle.img_dir}")
    private String imgDir;
    @Value("${file.oracle.config_path}")
    private String configPath;



    private String url_prefix = "https://" + bucketNameSpace + ".objectstorage."
            + Region.AP_CHUNCHEON_1.getRegionId() + ".oci.customer-oci.com";


    public ObjectStorage getClient() throws IOException {
        ConfigFile configFile = ConfigFileReader.parse(configPath, "DEFAULT");

        AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);

        return ObjectStorageClient.builder()
                .region(Region.AP_CHUNCHEON_1)
                .build(provider);
    }

    public UploadManager getManager(ObjectStorage client) throws Exception {
        UploadConfiguration configuration = UploadConfiguration.builder()
                .allowMultipartUploads(true)
                .allowParallelUploads(true)
                .build();
        return new UploadManager(client, configuration);
    }



    @Override
    public String createStoreName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos);
        String uuid = UUID.randomUUID().toString();
        return uuid + ext;
    }

    @Override
    public String storeFile(MultipartFile multipartFile) throws IOException {
        return "";
    }

    @Override
    public List<String> storeFiles(List<MultipartFile> multipartFileList) throws IOException {
        return List.of();
    }

    @Override
    public void removeFile(String fileDir) {

    }
}
