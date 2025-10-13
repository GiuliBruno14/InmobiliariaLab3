package com.giulietta.inmobiliaria.ui.login;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.giulietta.inmobiliaria.MainActivity;
import com.giulietta.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLogin = new MutableLiveData<>();
    private final Context context;
    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
    }
    public LiveData<Boolean> getLoginExitoso() {
        return mLogin;
    }
    public LiveData<String> getMensaje() {
        return mMensaje;
    }
    public void llamarLogin(String email, String clave){
        //Validar campos vacios
        if (email == null || email.isEmpty() || clave == null || clave.isEmpty()) {
            mMensaje.postValue("Ingrese email y contraseña");
            mLogin.postValue(false);
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.login(email, clave);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String token = response.body(); // en body llega el token
                    ApiClient.guardarToken(context, token);
                    mMensaje.postValue("Bienvenido");
                    mLogin.postValue(true); // aviso a la activity
                } else {
                    mMensaje.postValue("Usuario y/o contraseña incorrecta."+response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mMensaje.postValue("Error al conectar con el servidor");
            }
        });

    }

}
