package com.ElectronicStore.ElectronicStore.Service;

import com.ElectronicStore.ElectronicStore.Exception.BadApiRequest;
import com.ElectronicStore.ElectronicStore.Exception.ResourceNotFoundException;
import com.ElectronicStore.ElectronicStore.Service.Interface.FileService;
import com.ElectronicStore.ElectronicStore.util.FirebaseStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {


    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFileFirebase(MultipartFile file, String folderName) throws IOException {
        System.out.println("Resource Not found " + file.isEmpty());
        if (file.isEmpty())
            throw new ResourceNotFoundException("Please upload file");

        else {
            String originalFilename = file.getOriginalFilename();
            logger.info("Filename:{}",originalFilename);
            String filename  = UUID.randomUUID().toString();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileNameWithExtension= filename+extension;
            byte[] fileBytes = file.getBytes();


            if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
                return FirebaseStorage.upload(fileBytes, folderName, fileNameWithExtension);
            } else {
                throw new BadApiRequest("File with this " + extension + " not allowed !!");
            }
        }
    }

    @Override
    public String updateFileFirebase(MultipartFile file,String folderName , String oldUrl) throws IOException {
        if (file.isEmpty())
            throw new ResourceNotFoundException("Please upload file");

        String originalFilename = file.getOriginalFilename();
        logger.info("Filename:{}",originalFilename);
        String filename  = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension= filename+extension;
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
            if (FirebaseStorage.delete(oldUrl,folderName)) {
                return FirebaseStorage.upload(file.getBytes(), folderName, fileNameWithExtension);
            }
            else
                throw new RuntimeException("File Not Found");
        } else {
            throw new BadApiRequest("File with this " + extension + " not allowed !!");
        }
    }

    @Override
    public boolean deleteFileFirebase(String oldUrl,String folderName) throws IOException {
     return FirebaseStorage.delete(oldUrl,folderName);
    }


}
