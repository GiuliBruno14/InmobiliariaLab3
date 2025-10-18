package com.giulietta.inmobiliaria.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.giulietta.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetearPasswordViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje = new MutableLiveData<>();

    public LiveData<String> getmMensaje() {
        return mMensaje;
    }
    public ResetearPasswordViewModel(@NonNull Application application) {
        super(application);

    }

    public void enviarCorreoRecuperacion(String email) {
        if (email == null || email.isEmpty()) {
            mMensaje.postValue("Ingrese email");
            return;
        }
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.resetearPassword(email);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    mMensaje.postValue("Correo enviado");
                } else {
                    mMensaje.postValue("Error al enviar correo");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mMensaje.postValue("Error de conexión");
            }
        });
    }
}
