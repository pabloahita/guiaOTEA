package session;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class FileManager {
    private static FileManager instance;

    /** Azure Blob Service Client */
    private static BlobServiceClient blobServiceClient;

    /** Connection string for Azure Blob Storage */
    private static String connectionString = "DefaultEndpointsProtocol=https;AccountName=guiaotea;AccountKey=26jn+aC0S1u3EZCiFRPogNPz73Ot3/qKVHWn+s2OU3gu9Dkb1AHLz//qeI6l8WZ7VyxFV8Kmx+4c+ASt0Y0YKg==;EndpointSuffix=core.windows.net";


    private static BlobContainerClient containerClient;

    private static BlobClient blobClient;

    private FileManager(){
        this.blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
    }

    public static FileManager getInstance() {
        if (instance == null) {
            synchronized (FileManager.class) {
                if (instance == null) {
                    instance = new FileManager();
                }
            }
        }
        return instance;
    }

    public static void uploadFile(InputStream inputStream, String containerName, String fileName){
        Runnable task=()->{

            // Get the BlobContainerClient;
            containerClient = blobServiceClient.getBlobContainerClient(containerName);
            // Get the BlobClient
            blobClient = containerClient.getBlobClient(fileName);

            try {
                BinaryData data=BinaryData.fromStream(inputStream);
                blobClient.upload(data);
            }catch(Throwable t){
                if(!(t instanceof IllegalArgumentException)){//Exception appears, but file uploads correctly, obtaining a blob url
                    throw t;
                }
            }
        };

        task.run();
    }

    public static ByteArrayOutputStream downloadPhotoProfile(String fileName){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        Runnable task=()->{
            // Get the BlobContainerClient;
            containerClient = blobServiceClient.getBlobContainerClient("profile-photos");
            // Get the BlobClient
            blobClient = containerClient.getBlobClient(fileName);
            try{
                blobClient.downloadStream(stream);
            }catch(Throwable t){}
        };

        task.run();
        return stream;
    }

}
