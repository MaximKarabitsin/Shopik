package ru.sstu.shopik.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageProductService {
   void saveImage(MultipartFile[] files, long id) throws IOException;
   FileSystemResource getMainImage(Long id);
}
