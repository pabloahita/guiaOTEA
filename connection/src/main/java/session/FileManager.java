package session;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobStorageException;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import cli.indicators.IndicatorsEvaluation;
import cli.indicators.IndicatorsEvaluationEvidenceReg;
import cli.indicators.IndicatorsEvaluationIndicatorReg;
import cli.organization.Organization;
import cli.organization.data.EvaluatorTeam;
import cli.user.User;
import misc.DateFormatter;
import otea.connection.controller.RegionsController;


public class FileManager {
    private static FileManager instance;

    /** Azure Blob Service Client */
    private static BlobServiceClient blobServiceClient;

    /** Connection string for Azure Blob Storage */
    private static String connectionString = "DefaultEndpointsProtocol=https;AccountName=guiaotea;AccountKey=26jn+aC0S1u3EZCiFRPogNPz73Ot3/qKVHWn+s2OU3gu9Dkb1AHLz//qeI6l8WZ7VyxFV8Kmx+4c+ASt0Y0YKg==;EndpointSuffix=core.windows.net";


    private static BlobContainerClient containerClient;

    private static BlobClient blobClient;

    private static int numAttempts=0;

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
    /**Refresh API*/
    public static void refreshApi(){
        instance=new FileManager();
    }
    public static void uploadFile(InputStream inputStream, String containerName, String fileName){
        Runnable task=()->{

            // Get the BlobContainerClient;
            containerClient = blobServiceClient.getBlobContainerClient(containerName);


            // Get the BlobClient
            blobClient = containerClient.getBlobClient(fileName);

            try {
                BinaryData data=BinaryData.fromStream(inputStream);
                blobClient.upload(data,true);
            }catch(Throwable t){
                if(!(t instanceof IllegalArgumentException)){//Exception appears, but file uploads correctly, obtaining a blob url
                    throw t;
                }
            }
        };

        task.run();
    }


    public static CompletableFuture<ByteArrayOutputStream> downloadPhotoProfileAsync(String fileName){
        return CompletableFuture.supplyAsync(() -> {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Get the BlobContainerClient
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("profile-photos");
            // Get the BlobClient
            BlobClient blobClient = containerClient.getBlobClient(fileName);
            try {
                blobClient.downloadStream(stream);
                numAttempts=0;
            } catch (Exception e) {
                if(e.getCause() instanceof SocketTimeoutException){
                    numAttempts++;
                    if(numAttempts<3) {
                        return downloadPhotoProfileAsync(fileName).join();
                    }
                    else{
                        numAttempts=0;
                        return null;
                    }
                }
                else{
                    throw new RuntimeException(e);
                }
            }
            return stream;
        });
    }

    public static CompletableFuture<ByteArrayOutputStream> downloadReport(String fileName){
        return CompletableFuture.supplyAsync(() -> {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Get the BlobContainerClient
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("reports");
            // Get the BlobClient
            BlobClient blobClient = containerClient.getBlobClient(fileName);
            try {
                blobClient.downloadStream(stream);
                numAttempts=0;
            } catch (Exception e) {
                if(e.getCause() instanceof SocketTimeoutException){
                    numAttempts++;
                    if(numAttempts<3) {
                        return downloadPhotoProfileAsync(fileName).join();
                    }
                    else{
                        numAttempts=0;
                        return null;
                    }
                }
                else{
                    throw new RuntimeException(e);
                }
            }
            return stream;
        });
    }

    public static CompletableFuture<InputStream> downloadReportTemplate(String evaluationType) {
        return CompletableFuture.supplyAsync(() -> {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Get the BlobContainerClient
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("report-templates");
            // Get the BlobClient
            String blobName = "";
            if (evaluationType.equals("COMPLETE")) {
                blobName = "complete_ind_eval.docx";
            } else {
                blobName = "simple_ind_eval.docx";
            }
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            try {
                blobClient.downloadStream(stream);
                return new ByteArrayInputStream(stream.toByteArray());
            } catch (Exception e) {
                if (e.getCause() instanceof SocketTimeoutException) {
                    numAttempts++;
                    if (numAttempts < 3) {
                        return downloadReportTemplate(evaluationType).join();
                    } else {
                        numAttempts = 0;
                        return null;
                    }
                } else {
                    throw new RuntimeException(e);
                }
            }
        });
    }



}
