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

public class PerfilViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mMensaje = new MutableLiveData<>();;
    private final MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();
    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mutableNombreBt = new MutableLiveData<>();
    public LiveData<Propietario> getPropietarioLiveData(){
        return mPropietario;
    }
    public LiveData<String> getMensaje() {
        return mMensaje;
    }
    public LiveData<String> getNombreBt(){
        return mutableNombreBt;
    }
    public LiveData<Boolean> getEstado(){
        return mEstado;
    }
    public PerfilViewModel (@NonNull Application application){
        super(application);
    }
    public void cargarPerfil(Context context) {
        String token = ApiClient.leerToken(context);
        if(token == null){
            mMensaje.setValue("Por favor inicie sesión");
            return;
        }
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<Propietario> llamada = api.getPerfil("Bearer "+token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body()!= null){
                    mPropietario.postValue(response.body());
                } else {
                    mMensaje.postValue("Error al obtener perfil");
                }
            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                mMensaje.postValue("Error de conexion"+ t.getMessage());
            }
        });
    }

    public void guardar(String boton, String nombre, String apellido, String email,String dni, String telefono){
        if(boton.equalsIgnoreCase("Editar")){
            mEstado.setValue(true);
            mutableNombreBt.setValue("Guardar");
        } else {
            if (!validarCampos(nombre, apellido, email, dni, telefono)) {
                return; // sale si hay algún error
            }
            Propietario p = new Propietario();
            p.setId(mPropietario.getValue().getId());
            p.setNombre(nombre);
            p.setApellido(apellido);
            p.setEmail(email);
            p.setDni(dni);
            p.setTelefono(telefono);
            p.setCiudad(mPropietario.getValue().getCiudad());
            p.setDomicilio(mPropietario.getValue().getDomicilio());
            String token = ApiClient.leerToken(getApplication());
            // llamar al actualizar propietario
            Call<String> llamada =ApiClient.getApiInmobiliaria().actualizarPropietario("Bearer "+token, p);
            llamada.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        mutableNombreBt.setValue("Editar");
                        mEstado.setValue(false);
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

    public boolean validarCampos(String nombre, String apellido, String email, String dni, String telefono){
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || dni.isEmpty() || telefono.isEmpty()) {
            mMensaje.postValue("Por favor complete todos los campos");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mMensaje.postValue("Ingrese un correo válido");
            return false;
        }
        if (!dni.matches("\\d{7,8}")) {
            mMensaje.postValue("Ingrese un DNI válido");
            return false;
        }
        if (!telefono.matches("\\d{10,15}")) {
            mMensaje.postValue("Ingrese un teléfono válido");
            return false;
        }
        return true;
    }
}