package ru.sstu.shopik.services.impl;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sstu.shopik.services.ImageProductService;

import java.io.File;
import java.io.IOException;

@Service
public class ImageProductServiceImpl implements ImageProductService {

    public static String BIG_POSTFIX = "_big.png";
    public static String MEDIUM_POSTFIX = "_medium.png";
    public static String SMALL_POSTFIX = "_small.png";

    @Value("${project.manager.images.products.dir.path}")
    private String imagesProductsDirPath;

    @Override
    public void saveImage(MultipartFile[] files, long id) throws IOException {
        String pathDir = getImageDir(id);
        if (!new File(pathDir).exists()) {
            new File(pathDir).mkdirs();
        } else {
            FileUtils.cleanDirectory(new File(pathDir));
        }
        int i = 1;
        for (MultipartFile multipartFile : files) {

            String orgName = multipartFile.getOriginalFilename();
            String fullFilePath = pathDir + orgName;

            File dest = new File(fullFilePath);
            multipartFile.transferTo(dest);

            Thumbnails.of(dest).size(500, 500).toFile(new File(pathDir + i + BIG_POSTFIX));
            Thumbnails.of(dest).size(290, 290).toFile(new File(pathDir + i + MEDIUM_POSTFIX));
            Thumbnails.of(dest).size(100, 100).toFile(new File(pathDir + i + SMALL_POSTFIX));
            i++;
        }
        return;
    }


    private String getImageDir(long id) {
        String strId = Long.toString(id);
        if (strId.length() < 12) {
            strId = new String(new char[12 - strId.length()]).replace('\0', '0') + strId;
        }
        StringBuffer stringBuffer = new StringBuffer(strId);
        stringBuffer.insert(9, File.separator);
        stringBuffer.insert(6, File.separator);
        stringBuffer.insert(3, File.separator);
        return imagesProductsDirPath + stringBuffer + File.separator;
    }

    @Override
    public FileSystemResource getMainImage(Long id) {

        for (int i = 1; i <= 10; i++) {
            FileSystemResource fileSystemResource = this.getImage(id, i, MEDIUM_POSTFIX);
            if (fileSystemResource != null) {
                return fileSystemResource;
            }
        }
        return null;
    }

    private FileSystemResource getImage(Long id, int number, String postfix) {
        String imageFileName = getImageDir(id) + File.separator + number + postfix;

        File f = new File(imageFileName);
        if (f.exists() && !f.isDirectory()) {
            return new FileSystemResource(f);
        }

        return null;
    }
}
