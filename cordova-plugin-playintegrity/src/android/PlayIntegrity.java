package com.example;

import android.content.Context;

import com.google.android.gms.playintegrity.IntegrityManager;
import com.google.android.gms.playintegrity.IntegrityManagerFactory;
import com.google.android.gms.playintegrity.IntegrityTokenRequest;
import com.google.android.gms.playintegrity.IntegrityTokenResponse;
import com.google.android.gms.tasks.Task;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class PlayIntegrity extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("checkIntegrity")) {
            String nonce = data.getString(0);
            this.checkIntegrity(nonce, callbackContext);
            return true;
        }
        return false;
    }

    private void checkIntegrity(String nonce, CallbackContext callbackContext) {
        Context context = this.cordova.getActivity().getApplicationContext();

        IntegrityManager integrityManager = IntegrityManagerFactory.create(context);

        Task<IntegrityTokenResponse> integrityTokenResponse = integrityManager.requestIntegrityToken(
            IntegrityTokenRequest.builder().setNonce(nonce).build()
        );

        integrityTokenResponse.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Simplemente verifica si la tarea fue exitosa y devuelve un resultado basado en eso
                callbackContext.success("Integrity check passed");
            } else {
                callbackContext.error("Integrity check failed");
            }
        });
    }
}
