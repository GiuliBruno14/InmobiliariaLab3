package com.giulietta.inmobiliaria.ui.inmuebles;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.giulietta.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.giulietta.inmobiliaria.request.ApiClient;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mv;
    private FragmentDetalleInmuebleBinding binding;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);

        mv.getInmueble().observe(getViewLifecycleOwner(), inmueble -> {
            binding.etDireccion.setText(inmueble.getDireccion());
            binding.etUso.setText(inmueble.getUso());
            binding.etTipo.setText(inmueble.getTipo());
            binding.etAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
            binding.etLongitud.setText(inmueble.getLongitud());
            binding.etLatitud.setText(inmueble.getLatitud());
            binding.etPrecio.setText(String.valueOf(inmueble.getPrecio()));
            Glide.with(this)
                    .load(ApiClient.URLBASE + inmueble.getImagen())
                    .placeholder(null)
                    .error("null")
                    .into(binding.ivImagenInmueble);
            boolean estado = inmueble.getDisponible().equalsIgnoreCase("SI");
            binding.cbEstado.setChecked(estado);
        });
        mv.obtenerInmueble(getArguments());
        binding.cbEstado.setOnClickListener(view -> {
            mv.actualizarInmueble(binding.cbEstado.isChecked());
        });
        mv.getMensaje().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}