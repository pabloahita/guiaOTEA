package otea.connection.controller;

import com.azure.storage.blob.*;
import com.azure.identity.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.*;

public class ImageUploader {

    /** Controller instance */
    private static ImageUploader instance;

    /** Azure Blob Service Client */
    private static BlobServiceClient blobServiceClient;

    /** Container name in Azure Blob Storage */
    //private static String containerName = "profile-photos";

    /** Connection string for Azure Blob Storage */
    private static String connectionString = "DefaultEndpointsProtocol=https;AccountName=guiaotea;AccountKey=26jn+aC0S1u3EZCiFRPogNPz73Ot3/qKVHWn+s2OU3gu9Dkb1AHLz//qeI6l8WZ7VyxFV8Kmx+4c+ASt0Y0YKg==;EndpointSuffix=core.windows.net";


    private static BlobContainerClient containerClient;

    private static BlobClient blobClient;


    /** Class controller */
    private ImageUploader() {

        // Initialize the BlobServiceClient
        this.blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     */
    public static ImageUploader getInstance() {
        if (instance == null) {
            synchronized (ImageUploader.class) {
                if (instance == null) {
                    instance = new ImageUploader();
                }
            }
        }
        return instance;
    }

    /**
     * Uploads a file to Azure Blob Storage
     *
     * @param inputStream Stream
     * @return The URL of the uploaded blob
     */
    public static String uploadToBlobStorage(InputStream inputStream,String containerName,String fileName) throws IOException, ExecutionException, InterruptedException {
        try {
            String blobUrl="";
            ExecutorService executor1 = Executors.newSingleThreadExecutor();
            Callable<String> callable1= () -> {
                String url="";
                ExecutorService executor2 = Executors.newSingleThreadExecutor();
                Callable<String> callable2 = () -> {
                    // Get the BlobContainerClient
                    containerClient = blobServiceClient.getBlobContainerClient(containerName);

                    // Get the BlobClient
                    blobClient = containerClient.getBlobClient(fileName);

                    ExecutorService executorService3= Executors.newSingleThreadExecutor();
                    // Upload the file
                    executorService3.submit(()->{
                        blobClient.upload(inputStream);
                    });
                    String aux=blobClient.getBlobUrl();
                    executorService3.shutdown();
                    // Return the URL of the uploaded blob
                    return aux;
                };

                try {
                    Future<String> future2 = executor2.submit(callable2);
                    url = future2.get();
                    executor2.shutdown();
                    return url;
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    throw e;
                }
            };
            try {
                Future<String> future1 = executor1.submit(callable1);
                blobUrl = future1.get();
                executor1.shutdown();
                inputStream.close();
                return blobUrl;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw e;
            }

        }catch(IOException | InterruptedException | ExecutionException e){
            e.printStackTrace();
            throw e;
        }
    }

}
