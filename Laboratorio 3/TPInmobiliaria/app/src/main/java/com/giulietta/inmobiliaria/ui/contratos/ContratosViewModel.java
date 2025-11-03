package com.giulietta.inmobiliaria.ui.contratos;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.giulietta.inmobiliaria.modelo.Inmueble;
import com.giulietta.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mText = new MutableLiveData<>();
    private final MutableLiveData<List<Inmueble>> mInmuebles = new MutableLiveData<>();


    public ContratosViewModel(@NonNull Application application) {
        super(application);
        leerInmueblesConContratoVigente();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Inmueble>> getInmuebles() {
        return mInmuebles;
    }
    public void leerInmueblesConContratoVigente(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<List<Inmueble>> llamada = api.obtenerInmueblesConContratoVigente("Bearer " + token);

        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    List<Inmueble> inmuebles = response.body();
                    Log.d("VM_CONTRATOS", "Se recibieron " + (inmuebles != null ? inmuebles.size() : 0) + " inmuebles");
                    mInmuebles.postValue(inmuebles);
                } else {
                    mText.postValue("No hay inmuebles con contrato vigente");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                mText.postValue("Error de servidor: " + t.getMessage());
            }
        });
    }
}
