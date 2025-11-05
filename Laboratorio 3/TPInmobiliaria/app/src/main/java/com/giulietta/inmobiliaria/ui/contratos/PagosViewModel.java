package com.giulietta.inmobiliaria.ui.contratos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.giulietta.inmobiliaria.modelo.Pago;
import com.giulietta.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private MutableLiveData<String> mensaje = new MutableLiveData<>();
    private MutableLiveData<List<Pago>> pagos = new MutableLiveData<>();

    public PagosViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public LiveData<List<Pago>> getPagos() {
        return pagos;
    }

    public void leerPagos(int idContrato) {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<List<Pago>> llamada = api.ObtenerPagos("Bearer " + token,idContrato);
        /// Mensaje
        System.out.println("Hoola?");
        llamada.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    pagos.postValue(response.body());
                    mensaje.postValue("PAggosss");
                    System.out.println("Entrooooo");
                } else {
                    mensaje.postValue("No hay pagos registrados para estesi contrato");
                    System.out.println(" NOOOO Entrooooo");
                }
            }
            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                mensaje.postValue("Error de servidor: " + t.getMessage());
                System.out.println("SE ROMPIOOOOOOO" + t.getMessage());
            }
        });
    }
}