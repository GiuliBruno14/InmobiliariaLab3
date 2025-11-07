package com.giulietta.inmobiliaria;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.giulietta.inmobiliaria.modelo.Propietario;
import com.giulietta.inmobiliaria.request.ApiClient;
import com.giulietta.inmobiliaria.ui.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> propietario = new MutableLiveData<>();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        cargarPropietario();
    }

    public LiveData<Propietario> getPropietario(){
        return propietario;
    }
    public LiveData<String> getMensaje(){
        return mensaje;
    }

    private void cargarPropietario() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();

        Call<Propietario> llamada = api.getPerfil("Bearer " + token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    propietario.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                mensaje.postValue("Error de conexión");
            }
        });
    }

    public void cerrarSesion(){
        Context context = getApplication().getApplicationContext();
        ApiClient.borrarToken(context);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //No pueda volver
        context.startActivity(intent);
    }
}
