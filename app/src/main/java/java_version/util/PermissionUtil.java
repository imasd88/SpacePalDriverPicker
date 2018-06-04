package java_version.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * Created by sidhu on 6/4/2018.
 */

public class PermissionUtil{

    /**
     * Requesting location permission
     * This uses single permission model from dexter
     * Once the permission granted, show's camera
     * On permanent denial opens settings dialog
     */
    public static void requestCameraPermission(Activity activity,PermissionCallback permissionCallback) {
        Dexter.withActivity(activity)
                .withPermission(
                        Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {


                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        permissionCallback.onPermissionGranted();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        permissionCallback.onPermissionDenied();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).
                withErrorListener(error -> Toast.makeText(activity, "Error occurred!", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    public static boolean isCameraPermissionGranted(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public interface PermissionCallback{
        void onPermissionGranted();
        void onPermissionDenied();
    }
}