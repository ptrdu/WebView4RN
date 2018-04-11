package com.hpr.module.camera;

import android.content.Intent;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.hpr.module.common.Constant;

import java.util.HashMap;

/**
 * Created by me2 on 2018/4/10.
 */

public class CameraModule extends ReactContextBaseJavaModule {
    private static final int CAMERA_QRCODE_SCANNER_REQ_CODE = 55121;
    private HashMap<Integer, Promise> qrCodeCallbackMap;

    public CameraModule(ReactApplicationContext reactContext) {
        super(reactContext);
        qrCodeCallbackMap = new HashMap<>();
    }

    @Override
    public String getName() {
        return "CameraModule";
    }

    private int randomID () {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }

    @ReactMethod
    public void QRCodeScanner(Promise promise) {
        Intent intent = new Intent(getCurrentActivity(), CameraCodeScannerActivity.class);

        int callbackId = randomID();
        Integer callbackIdNum = new Integer(callbackId);

        qrCodeCallbackMap.put(callbackIdNum, promise);
        intent.putExtra("callbackId", callbackId);

        getCurrentActivity().startActivityForResult(intent, CAMERA_QRCODE_SCANNER_REQ_CODE);
    }

    public boolean handleActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == CAMERA_QRCODE_SCANNER_REQ_CODE) {
            String result = data.getStringExtra("result");
            int callbackId = data.getIntExtra("callbackId", 0);

            if (callbackId == 0) {
                return false;
            }

            Integer callbackIdNum = new Integer(callbackId);

            Promise promise = qrCodeCallbackMap.get(callbackIdNum);

            if (promise != null) {
                if (resultCode == Constant.RESULT_OK) {
                    promise.resolve(result);
                } else {
                    promise.reject(Integer.toString(resultCode), result);
                }

                qrCodeCallbackMap.remove(callbackIdNum);
            }
        }

        // Your custom handling logic
        return true;
    }
}