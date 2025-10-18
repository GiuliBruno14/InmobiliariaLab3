package com.giulietta.inmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.giulietta.inmobiliaria.modelo.Propietario;
import com.giulietta.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarPasswordViewModel extends AndroidViewModel {
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private MutableLiveData<Boolean> cambioEstado = new MutableLiveData<>();

    public CambiarPasswordViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getmMensaje() {
        return mMensaje;
    }
    public LiveData<Boolean> getCambioExitoso() {
        return cambioEstado;
    }

    public void cambiarPassword(Context context, String actual, String nueva1, String nueva2){
        if(actual == null || actual.isEmpty() || nueva1==null || nueva1.isEmpty() ||nueva2==null || nueva2.isEmpty()){
            mMensaje.postValue("Complete todos los campos");
            return;
        }
        if(!nueva1.equals(nueva2)){
            mMensaje.postValue("Las contraseñas no coinciden");
            return;
        }
        String token = ApiClient.leerToken(context);
        if(token == null){
            mMensaje.setValue("Por favor inicie sesión");
            return;
        }
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.cambiarPassword("Bearer "+token, actual, nueva1);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    mMensaje.postValue("Contraseña actualizada correctamente");
                    cambioEstado.postValue(true);
                } else {
                    mMensaje.postValue("Error al actualizar contraseña");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                    mMensaje.postValue("Error de conexón"+t.getMessage());
            }
        });

    }
}