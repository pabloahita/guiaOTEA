package gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import session.FileManager;
import session.Session;

public class ProfilePhotoUtil {

    private Bitmap imgUser;

    private Bitmap imgOrg;

    private String profilePhotoUsr;

    private String profilePhotoOrg;

    private static ProfilePhotoUtil instance;


    CompletableFuture<Void> allOf;

    CompletableFuture<ByteArrayOutputStream> orgPhotoFuture;

    CompletableFuture<ByteArrayOutputStream> userPhotoFuture;


    private ProfilePhotoUtil(String profilePhotoUsr,String profilePhotoOrg){
        this.profilePhotoUsr = profilePhotoUsr;
        this.profilePhotoOrg = profilePhotoOrg;

        List<String> fileNames=new ArrayList<>();
        if(!profilePhotoUsr.isEmpty()) {
            fileNames.add(profilePhotoUsr);
        }
        if(!profilePhotoOrg.isEmpty()) {
            fileNames.add(profilePhotoOrg);
        }

        if(!fileNames.isEmpty()) {
            // Descargar fotos en paralelo
            FileManager.downloadPhotosProfileAsync(fileNames, new FileManager.PhotosDownloadCallback() {
                @Override
                public void onPhotoDownloadSuccess(String fileName, ByteArrayOutputStream stream) {
                    if (fileName.equals(profilePhotoUsr)) {
                        imgUser = getBitmapFromStream(stream);
                    } else {
                        imgOrg = getBitmapFromStream(stream);
                    }
                }

                @Override
                public void onPhotoDownloadFailure(String fileName, Exception e) {

                }


            });
        }
    }


    public static synchronized ProfilePhotoUtil getInstance(){return instance;}

    public static synchronized ProfilePhotoUtil createInstance(String profilePhotoUsr,String profilePhotoOrg){
        if(instance==null){
            instance=new ProfilePhotoUtil(profilePhotoUsr,profilePhotoOrg);
        }
        return instance;
    }

    public static synchronized void logout(){
        instance=null;
    }

    /*public void updatePhotoOrg(String profilePhotoOrg){
        instance.profilePhotoOrg=profilePhotoOrg;
        if(!profilePhotoOrg.isEmpty()){
            instance.imgOrg = instance.getBitmapFromStream(instance.getProfilePhoto(profilePhotoOrg));
        }
    }

    public void updatePhotoUser(String profilePhotoUsr){
        instance.profilePhotoUsr=profilePhotoUsr;
        if(!profilePhotoUsr.isEmpty()){
            instance.imgUser = instance.getBitmapFromStream(instance.getProfilePhoto(profilePhotoUsr));
        }
    }*/

    public Bitmap getImgUser() {
        return imgUser;
    }

    public void setImgUser(Bitmap imgUser) {
        this.imgUser = imgUser;
    }

    public Bitmap getImgOrg() {
        return imgOrg;
    }

    public void setImgOrg(Bitmap imgOrg) {
        this.imgOrg = imgOrg;
    }

    public static Bitmap getBitmapFromStream(ByteArrayOutputStream stream){
        byte[] data=stream.toByteArray();
        return BitmapFactory.decodeByteArray(data,0,data.length);
    }
}