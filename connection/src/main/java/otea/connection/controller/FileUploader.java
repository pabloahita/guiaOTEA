package otea.connection.controller;

import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.*;

import okhttp3.ResponseBody;
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
     * @param inputStream Stream
     */
    public static String uploadFile(InputStream inputStream, String containerName, String fileName){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        byte[] data=getFileBytes(inputStream);
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Call<ResponseBody> call = api.uploadFile(data, containerName, fileName);
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
