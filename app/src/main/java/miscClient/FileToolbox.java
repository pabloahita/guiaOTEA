package miscClient;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Auxiliar class to download the images from the internet
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class FileToolbox {

    /**Downloader instance*/
    private static FileToolbox instance;

    private static File cacheDir;

    private static ContentResolver contentResolver;
    
    /**
     * Class constructor
     * */
    private FileToolbox(File cacheDir, ContentResolver contentResolver){
        this.cacheDir=cacheDir;
        this.contentResolver=contentResolver;
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Instance of the downloader
     * */
    public static FileToolbox getInstance(){
        return instance;
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Instance of the downloader
     * */
    public static FileToolbox getInstance(File cacheDir, ContentResolver contentResolver){
        if(instance==null){
            synchronized (FileToolbox.class){
                if(instance==null){
                    instance=new FileToolbox(cacheDir,contentResolver);
                }
            }
        }
        return instance;
    }

    /**
     * Method that returns an image using its HTTP address
     *
     * @param imageHttpAddress - HTTP address
     * @return Loaded image
     * */
    public static Bitmap downloadImg(String imageHttpAddress) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Bitmap> callable = new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                URL imageUrl = new URL(imageHttpAddress);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                Bitmap loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                return loadedImage;
            }
        };
        try {
            Future<Bitmap> future = executor.submit(callable);
            Bitmap image = future.get();
            executor.shutdown();
            return image;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static File createFileFromUri(Uri uri) throws IOException {
        // Crear un archivo temporal en el almacenamiento privado de la aplicación
        File file = new File(cacheDir, "tempFile");
        file.createNewFile();

        // Copiar el contenido del Uri al archivo
        try (InputStream in = contentResolver.openInputStream(uri);
             OutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }

        return file;
    }

}
