package com.giulietta.inmobiliaria.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends AndroidViewModel {

    private final MutableLiveData<LatLng> ubicacion = new MutableLiveData<>();
    private GoogleMap googleMap;
    public InicioViewModel(@NonNull Application application) {
        super(application);
        obtenerUbicacionFija();

    }
    public LiveData<LatLng> getUbicacion(){
        return ubicacion;
    }
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;
        // Si la ubicación ya está disponible, movemos la cámara.
        if (ubicacion.getValue() != null) {
            moverCamaraYMarcar(ubicacion.getValue());
        }
    }

    private void obtenerUbicacionFija() {
        LatLng inmobiliaria = new LatLng(-33.2967, -66.3356);
        this.ubicacion.setValue(inmobiliaria);

        if (googleMap != null) {
            moverCamaraYMarcar(inmobiliaria);
        }
    }
    private void moverCamaraYMarcar(LatLng latLng) {
        googleMap.clear(); // Limpia marcadores anteriores si los hubiera
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Inmobiliaria Giulietta"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f));
    }
}