package session;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

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

    public static void downloadPhotosProfileAsync(List<String> fileNames, final PhotosDownloadCallback callback) {
        // Contador para esperar a que todas las descargas se completen
        CountDownLatch latch = new CountDownLatch(fileNames.size());
        List<ByteArrayOutputStream> resultStreams = new ArrayList<>();

        for (String fileName : fileNames) {
            // Get the BlobContainerClient
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("profile-photos");
            // Get the BlobClient
            BlobClient blobClient = containerClient.getBlobClient(fileName);

            if(!fileName.isEmpty()) {
                // Ejecutar cada descarga en un hilo separado
                new Thread(() -> {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    try {
                        blobClient.downloadStream(stream);
                        numAttempts = 0;
                        // Llamar al callback en caso de éxito
                        callback.onPhotoDownloadSuccess(fileName, stream);
                    } catch (Exception e) {
                        if (e.getCause() instanceof SocketTimeoutException) {
                            numAttempts++;
                            if (numAttempts < 3) {
                                // Intentar nuevamente la descarga recursivamente
                                downloadPhotosProfileAsync(Collections.singletonList(fileName), callback);
                            } else {
                                numAttempts = 0;
                                // Llamar al callback en caso de falla después de varios intentos
                                callback.onPhotoDownloadFailure(fileName, new RuntimeException("Número máximo de intentos alcanzado", e));
                            }
                        } else {
                            // Llamar al callback en caso de otro tipo de error
                            callback.onPhotoDownloadFailure(fileName, new RuntimeException("Error en la descarga", e));
                        }
                    } finally {
                        latch.countDown(); // Reducir el contador del latch cuando una descarga se completa
                    }
                }).start();
            }else{

            }
        }

        try {
            latch.await(); // Esperar hasta que todas las descargas se completen
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // Interfaz para el callback de descarga de fotos
    public interface PhotosDownloadCallback {
        void onPhotoDownloadSuccess(String fileName, ByteArrayOutputStream stream);
        void onPhotoDownloadFailure(String fileName, Exception e);
    }

    public interface ReportsDeletionCallback{
        void onSucess();

        void onFailure();
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
                        return downloadReport(fileName).join();
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

    public static void deleteBlob(String containerName, String blobName) {
        new Thread(()->{
            // Obtener el BlobContainerClient
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            // Obtener el BlobClient
            BlobClient blobClient = containerClient.getBlobClient(blobName);

            try {
                // Eliminar el blob
                blobClient.delete();
            } catch (BlobStorageException e) {
                throw new RuntimeException("Error al eliminar el blob", e);
            }
        }).start();
    }




}
