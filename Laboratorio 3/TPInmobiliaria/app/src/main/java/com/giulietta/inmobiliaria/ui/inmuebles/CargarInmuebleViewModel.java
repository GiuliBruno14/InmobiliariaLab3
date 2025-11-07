package com.giulietta.inmobiliaria.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.giulietta.inmobiliaria.modelo.Inmueble;
import com.giulietta.inmobiliaria.modelo.TipoInmuebles;
import com.giulietta.inmobiliaria.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CargarInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> mUri = new MutableLiveData<>();
    private MutableLiveData<String> mText = new MutableLiveData<>();
    private MutableLiveData<List<TipoInmuebles>> mTipos = new MutableLiveData<>();

    public CargarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Uri> getUri() {
        return mUri;
    }
    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<List<TipoInmuebles>> getTipos() {
        return mTipos;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            mUri.setValue(uri);
        }
    }
    public void agregarInmueble(String direccion, String uso, String tipo, int ambientes, int latitud, int longitud, double precio){
        try {
            if (direccion.isEmpty() || uso.isEmpty() || tipo.isEmpty() || ambientes == 0 || latitud == 0 || longitud == 0) {
                mText.setValue("Debe completar todos los campos");
            } else {
                //Parsear
                String latitudStr = String.valueOf(latitud);
                String longitudStr = String.valueOf(longitud);
                //Verificar si hay foto
                if (mUri.getValue() == null) {
                    mText.setValue("Debe seleccionar una imagen");
                }
                Inmueble inmueble = new Inmueble();
                inmueble.setDireccion(direccion);
                inmueble.setUso(uso);
                inmueble.setTipo(tipo);
                inmueble.setAmbientes(ambientes);
                inmueble.setLatitud(latitudStr);
                inmueble.setLongitud(longitudStr);
                inmueble.setPrecio(precio);
                inmueble.setDisponible("SI");
                byte[] imagen = transformarImagen();
                String inmuebleJson = new Gson().toJson(inmueble);
                RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
                MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);
                String token = ApiClient.leerToken(getApplication());
                ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
                Call<Inmueble> llamada = api.CargarInmueble("Bearer " + token, imagenPart, inmuebleBody);
                llamada.enqueue(new Callback<Inmueble>() {
                    @Override
                    public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                        if (response.isSuccessful()) {
                            mText.setValue("Inmueble cargado correctamente");
                        } else {
                            mText.setValue("Error al cargar el inmueble: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable t) {
                        mText.setValue("Error de servidor: " + t.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            mText.setValue("Error: " + e.getMessage());
        }
    }

    public void obtenerTipos(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<List<TipoInmuebles>> llamada = api.obtenerTipos();
        llamada.enqueue(new Callback<List<TipoInmuebles>>() {
            @Override
            public void onResponse(Call<List<TipoInmuebles>> call, Response<List<TipoInmuebles>> response) {
                if(response.isSuccessful()){
                    mTipos.postValue(response.body());
                    Log.d("TIPOS", "Cantidad de tipos: " + response.body().size());
                } else
                    mText.postValue("No hay tipos de inmuebles disponibles para este propietario");
            }
            @Override
            public void onFailure(Call<List<TipoInmuebles>> call, Throwable t) {
                mText.postValue("Error de servidor: "+t.getMessage());
            }
        });
    }

    private byte[] transformarImagen(){
        try  {
            Uri uri = mUri.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);//Crea un canal para conectar
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException er) {
            mText.setValue("Error: " + er.getMessage());
            return new byte[]{};
        }
    }

}