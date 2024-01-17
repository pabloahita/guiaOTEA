package gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
public class ImageDownloader {

    /**Downloader instance*/
    private static ImageDownloader instance;
    
    /**
     * Class constructor
     * */
    private ImageDownloader(){
        
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Instance of the downloader
     * */
    public static ImageDownloader getInstance(){
        if(instance==null){
            synchronized (ImageDownloader.class){
                if(instance==null){
                    instance=new ImageDownloader();
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

}
