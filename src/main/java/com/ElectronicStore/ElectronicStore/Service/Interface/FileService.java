package com.ElectronicStore.ElectronicStore.Service.Interface;

import jakarta.mail.Multipart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String uploadFileFirebase(MultipartFile file,String folderName) throws IOException;

    String updateFileFirebase(MultipartFile file,String folderName,String oldUrl) throws IOException;

    boolean deleteFileFirebase(String oldUrl,String folderName) throws IOException;


}
