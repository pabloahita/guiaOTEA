package otea.connection.controller;


import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.*;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import otea.connection.ConnectionClient;
import otea.connection.api.FileUploaderApi;
import otea.connection.api.TranslatorApi;
import retrofit2.Call;
import retrofit2.Response;

public class FileUploader {

    /** Controller instance */
    private static FileUploader instance;




    /** Class constructor */
    private FileUploader() {


    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     */
    public static FileUploader getInstance() {
        if (instance == null) {
            synchronized (FileUploader.class) {
                if (instance == null) {
                    instance = new FileUploader();
                }
            }
        }
        return instance;
    }

    /**
     * Uploads a file to Azure Blob Storage
     *
     * @param inputStream - File to upload
     * @param containerName - Container where the file will be hosted
     * @param fileName - File name
     */



}
