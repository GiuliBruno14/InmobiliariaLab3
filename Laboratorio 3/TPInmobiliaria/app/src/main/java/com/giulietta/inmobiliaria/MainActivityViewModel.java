package com.giulietta.inmobiliaria;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.giulietta.inmobiliaria.request.ApiClient;
import com.giulietta.inmobiliaria.ui.login.LoginActivity;

public class MainActivityViewModel extends AndroidViewModel {

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void cerrarSesion(){
        Context context = getApplication().getApplicationContext();
        ApiClient.borrarToken(context);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //No pueda volver
        context.startActivity(intent);
    }
}
