package gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
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

    private ProfilePhotoUtil(String profilePhotoUsr,String profilePhotoOrg){
        this.profilePhotoUsr = profilePhotoUsr;
        this.profilePhotoOrg = profilePhotoOrg;

        // Descargar fotos en paralelo
        CompletableFuture<ByteArrayOutputStream> userPhotoFuture = profilePhotoUsr.isEmpty() ?
                CompletableFuture.completedFuture(new ByteArrayOutputStream()) :
                FileManager.downloadPhotoProfileAsync(profilePhotoUsr);

        CompletableFuture<ByteArrayOutputStream> orgPhotoFuture = profilePhotoOrg.isEmpty() ?
                CompletableFuture.completedFuture(new ByteArrayOutputStream()) :
                FileManager.downloadPhotoProfileAsync(profilePhotoOrg);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(userPhotoFuture, orgPhotoFuture);

        allOf.thenRun(() -> {
            try {
                if (!profilePhotoUsr.isEmpty()) {
                    ByteArrayOutputStream userStream = userPhotoFuture.get();
                    imgUser = getBitmapFromStream(userStream);
                }
                if (!profilePhotoOrg.isEmpty()) {
                    ByteArrayOutputStream orgStream = orgPhotoFuture.get();
                    imgOrg = getBitmapFromStream(orgStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).join();
    }

    public static synchronized ProfilePhotoUtil getInstance(){return instance;}

    public static synchronized ProfilePhotoUtil createInstance(String profilePhotoUsr,String profilePhotoOrg){
        if(instance==null){
            instance=new ProfilePhotoUtil(profilePhotoUsr,profilePhotoOrg);
        }
        return instance;
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

    /*private ByteArrayOutputStream getProfilePhoto(String profilePhotoUrl){
        return FileManager.downloadPhotoProfile(profilePhotoUrl);
    }*/

    private Bitmap getBitmapFromStream (ByteArrayOutputStream stream){
        byte[] data=stream.toByteArray();
        return BitmapFactory.decodeByteArray(data,0,data.length);
    }
}
