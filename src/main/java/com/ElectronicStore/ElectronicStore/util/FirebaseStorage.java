package com.ElectronicStore.ElectronicStore.util;

import com.ElectronicStore.ElectronicStore.Exception.BadApiRequest;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Component
public class FirebaseStorage {

    private static final String PROJECT_ID = "electronic-sto";
    private static final String BUCKET_NAME = "electronic-sto.appspot.com";

    private static String uploadFile(byte[] fileBytes, String fileName, String folderName) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, folderName + '/' + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/"+fileName.substring(fileName.lastIndexOf(".")+1)).build();

        InputStream credentialsStream = FirebaseStorage.class.getClassLoader()
                .getResourceAsStream("electronic-sto-firebase-adminsdk-lddmz-61517bacdb.json");
        Credentials credentials = GoogleCredentials.fromStream(credentialsStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
         storage.create(blobInfo, fileBytes);

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME + "/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, java.net.URLEncoder.encode(folderName + '/' + fileName, "UTF-8"));


    }

    public static String upload(byte[] fileBytes, String folderName, String fileName) throws IOException {
        // This unique filename needs to be configured at the database call
        String extension = fileName.substring(fileName.lastIndexOf("."));

        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
            String URL = uploadFile(fileBytes, fileName, folderName);
            return URL;
        } else {
            throw new BadApiRequest("File with this " + extension + " not allowed !!");
        }
    }

    public static boolean delete(String fileUrl ,String folderName)throws IOException {
        URL url = new URL(fileUrl);

//        String objectName = url.getPath().substring(1);

        System.out.println("url: "+ url);
        String objectName = url.getPath().split("/o/")[1];
        objectName = folderName + '/' + objectName.substring(folderName.length());

        System.out.println("ObjectName: "+ objectName);
        InputStream serviceAccount = FirebaseStorage.class.getClassLoader()
                .getResourceAsStream("electronic-sto-firebase-adminsdk-lddmz-61517bacdb.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId(PROJECT_ID).build().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        return  storage.delete(blobId);
    }


}
