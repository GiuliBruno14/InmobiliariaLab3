package com.giulietta.inmobiliaria.ui.perfil;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.giulietta.inmobiliaria.modelo.Propietario;
import com.giulietta.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends ViewModel {

    private final MutableLiveData<String> mMensaje = new MutableLiveData<>();;
    private final MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();
    public LiveData<Propietario> getPropietarioLiveData(){
        return mPropietario;
    }
    public LiveData<String> getErrorLiveData() {
        return mMensaje;
    }
    public void cargarPerfil(Context context) {
        String token = ApiClient.leerToken(context);
        if(token == null){
            mMensaje.setValue("Por favor inicie sesión");
            return;
        }
        ApiClient.InmobiliariaService service = ApiClient.getApiInmobiliaria();
        Call<Propietario> llamada = service.getPerfil("Bearer "+token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body()!= null){
                    mPropietario.setValue(response.body());
                } else {
                    mMensaje.setValue("Error al obtener perfil");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                mMensaje.setValue("Error de conexion"+ t.getMessage());
            }
        });
    }
}