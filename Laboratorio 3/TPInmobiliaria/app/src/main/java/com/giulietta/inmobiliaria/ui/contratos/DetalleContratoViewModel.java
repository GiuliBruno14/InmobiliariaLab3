package com.giulietta.inmobiliaria.ui.contratos;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.giulietta.inmobiliaria.modelo.Contrato;
import com.giulietta.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    MutableLiveData<Contrato> contrato = new MutableLiveData<>();
    MutableLiveData<String> mensaje = new MutableLiveData<>();
    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Contrato> getContrato() {
        return contrato;
    }
    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public void obtenerContrato(Bundle bundle){
        if(bundle == null){
            return;
        }
        int idInmueble = bundle.getInt("idInmueble");
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<Contrato> llamada = api.ObtenerContrato("Bearer "+token, idInmueble);
        llamada.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()){
                    contrato.postValue(response.body());
                } else {
                    mensaje.postValue("Error al obtener el contrato: HTTP "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                mensaje.postValue("Error de conexión");
            }
        });

    }

}