package otea.connection.controller;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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


    /**API*/
    private static FileUploaderApi api;

    /** Class constructor */
    private FileUploader() {
        api= ConnectionClient.getInstance().getRetrofit().create(FileUploaderApi.class);
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
     * @param file - File to upload
     * @param containerName - Container where the file will be hosted
     * @param fileName - File name
     * @param fileType - File type
     * @param fileExtension - File extension
     */
    public static String uploadFile(File file, String containerName, String fileName, String fileType, String fileExtension){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        RequestBody description = RequestBody.create(file, MediaType.parse(fileExtension));

        MultipartBody.Part body = MultipartBody.Part.createFormData(fileType, fileName, description);

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Call<ResponseBody> call = api.uploadFile(body,containerName);
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<String> future = executor.submit(callable);
            String result = future.get();
            executor.shutdown();
            return result;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static byte[] getFileBytes(InputStream inputStream) {
        try{
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[] byteArray = buffer.toByteArray();
            return byteArray;
        }catch (Throwable t){
            return null;
        }
    }

}
