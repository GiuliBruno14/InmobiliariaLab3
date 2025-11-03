package com.giulietta.inmobiliaria.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.giulietta.inmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.giulietta.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.giulietta.inmobiliaria.request.ApiClient;
import com.giulietta.inmobiliaria.ui.inmuebles.DetalleInmuebleViewModel;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel mv;
    private FragmentDetalleContratoBinding binding;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);

        mv.getContrato().observe(getViewLifecycleOwner(), contrato -> {
            binding.etIdContrato.setText(String.valueOf(contrato.getId()));
            String inicioFormateado = DateFormat.format("dd/MM/yyyy", contrato.getFechaInicio()).toString();
            binding.etFechaInicio.setText(inicioFormateado);
            String finFormateado = DateFormat.format("dd/MM/yyyy", contrato.getFechaTerm()).toString();
            binding.etFechaFin.setText(finFormateado);
            binding.etMonto.setText(String.valueOf(contrato.getMontoMensual()));
            binding.etInquilino.setText(contrato.getArrendatario().getNombre()+" "+contrato.getArrendatario().getApellido());
            binding.etIdInmueble.setText(String.valueOf(contrato.getIdInmueble()));
            binding.etDireccionInmueble.setText(contrato.getDatos().getDireccion());
        });
        mv.obtenerContrato(getArguments());
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