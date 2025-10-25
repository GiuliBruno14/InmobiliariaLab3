package com.giulietta.inmobiliaria.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.giulietta.inmobiliaria.modelo.Inmueble;
import com.giulietta.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmueble = new MutableLiveData<>();
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getInmueble(){
        return inmueble;
    }
    public LiveData<String> getMensaje() {
        return mMensaje;
    }
    public void obtenerInmueble(Bundle bundle){
        if (bundle == null) {
            return; // No hacer nada si no hay bundle
        }
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        if(inmueble !=null){
            this.inmueble.setValue(inmueble);
        }
    }
    public void actualizarInmueble(Boolean disponible) {
        Inmueble i = inmueble.getValue();
        if (i == null) {
            mMensaje.postValue("Error: No se pudo obtener el inmueble");
            return;
        }
        if (disponible) {
            i.setDisponible("SI");
        } else {
            i.setDisponible("NO");
        }
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.actualizarInmueble("Bearer "+token, i);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    mMensaje.postValue("Datos actualizados correctamente");
                } else {
                    mMensaje.postValue("Error al actualizar los datos: HTTP "+response.code());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mMensaje.postValue("Error de conexion "+t.getMessage());
            }
        });



    }
}
