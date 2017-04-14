package com.dianzhi.bookdemo.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CK on 2017/4/12.
 * custom manager permission
 */

public class Permission {

    public Permission() {
    }
    public  static class Builder {
        private List<String> shouldRequestPermissions;
        private List<Integer> denied;
        private List<Boolean> tips;
        private Activity activity;
        private Fragment fragment;
        private PermissionListener permissionListener;
        private final int PERMISSIONS_REQUEST_CODE = 42;

        public Builder() {

        }

        public Permission build() {
            return new Permission();
        }

        public Builder activity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder fragment(Fragment fragment) {
            this.fragment = fragment;
            return this;
        }
        /***
         * request permission for activity's method of up 6.0
         * @param permissions
         * @param listener
         */
        public void requestPermissionFeature(String[] permissions, PermissionListener listener) {
            if (activity == null)
                return;
            shouldRequestPermissions = new ArrayList<>();
            denied = new ArrayList<>();
            tips = new ArrayList<>();
            this.permissionListener = listener;
            if (isNeedRequestPermission()) {
                for (int i = 0; i < permissions.length; i++) {
                    if (ContextCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED)
                        shouldRequestPermissions.add(permissions[i]);
                }
                if (shouldRequestPermissions.size() > 0) {
                    String[] requestPermissions = shouldRequestPermissions.toArray(new String[shouldRequestPermissions.size()]);
                    ActivityCompat.requestPermissions(activity, requestPermissions, PERMISSIONS_REQUEST_CODE);
                } else {
                    if (permissionListener != null)
                        permissionListener.granted();

                }

            } else {
                if (permissionListener != null)
                    permissionListener.granted();
            }
        }

        /***
         * request permission for fragment's method of up 6.0
         * @param permissions
         * @param listener
         */
        public void requestPermissionForFragment(String[] permissions, PermissionListener listener) {
            if (fragment == null)
                return;
            shouldRequestPermissions = new ArrayList<>();
            denied = new ArrayList<>();
            tips = new ArrayList<>();
            this.permissionListener = listener;
            if (isNeedRequestPermission()) {
                for (int i = 0; i < permissions.length; i++) {
                    if (ContextCompat.checkSelfPermission(fragment.getContext(), permissions[i]) != PackageManager.PERMISSION_GRANTED)
                        shouldRequestPermissions.add(permissions[i]);
                }
                if (shouldRequestPermissions.size() > 0) {
                    String[] requestPermissions = shouldRequestPermissions.toArray(new String[shouldRequestPermissions.size()]);

                    fragment.requestPermissions(requestPermissions, PERMISSIONS_REQUEST_CODE);
                } else {
                    if (permissionListener != null)
                        permissionListener.granted();
                }

            } else {
                if (permissionListener != null)
                    permissionListener.granted();
            }
        }

        /**
         * this callback of all request permission
         * @param requestCode
         * @param permissions
         * @param grantResults
         */
        public void RequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (requestCode != PERMISSIONS_REQUEST_CODE)
                return;
            for (int i = 0; i < permissions.length; i++) {
                boolean isTip=false;
                if (activity != null) {
                    isTip = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]);
                } else {
                    isTip = ActivityCompat.shouldShowRequestPermissionRationale(fragment.getActivity(), permissions[i]);
                }
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    denied.add(i);
                    if (!isTip)
                        tips.add(isTip);
                }
            }
            if (denied.size() > 0) {
                if (permissionListener != null) {
                    if (tips.size() > 0)
                        permissionListener.deniedNeverAsk();
                    else
                        permissionListener.denied();
                }

            } else {
                if (permissionListener != null)
                    permissionListener.granted();
            }
        }
    }

    private static boolean isNeedRequestPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? true : false;
    }

    public interface PermissionListener {
        void granted();

        void denied();

        void deniedNeverAsk();
    }

}
