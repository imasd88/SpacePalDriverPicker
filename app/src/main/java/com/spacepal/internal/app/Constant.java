package com.spacepal.internal.app;

/**
 * Created by sidhu on 4/18/2018.
 */

public interface Constant {

    String CLIENT_ID="spacePAL.android";
    String CLIENT_SECRET="bace9fda-87f3-4a09-a68f-5fc442b9acd4";
    String GRANT_TYPE="password";

    String ROLE_PICKER="PICKER";
    String ROLE_DRIVER="DRIVER";

    int PRIORITY_H=1;
    int PRIORITY_M=0;
    int PRIORITY_L=-1;

    interface AssignmentType{
        String INITIAL_SCAN="INITIAL_SCAN";
        String SHELF_TO_BAY="SHELF_TO_BAY";
        String BUNDLE_TO_BAY="BUNDLE_TO_BAY";
        String BAY_TO_CUSTOMER="BAY_TO_CUSTOMER";
        String TO_ORDER="TO_ORDER";
        String TO_CUSTOMER="TO_CUSTOMER";
    }

    interface PicModes {
        String CAMERA = "Camera";
        String GALLERY = "Gallery";
        String CANCEL = "Cancel";
    }
}
