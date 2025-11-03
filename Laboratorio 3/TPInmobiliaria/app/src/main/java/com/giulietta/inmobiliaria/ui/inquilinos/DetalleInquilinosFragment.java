package com.giulietta.inmobiliaria.ui.inquilinos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.giulietta.inmobiliaria.databinding.FragmentDetalleInquilinosBinding;
import com.giulietta.inmobiliaria.ui.contratos.DetalleContratoViewModel;

public class DetalleInquilinosFragment extends Fragment {

    private DetalleInquilinosViewModel mv;
    private FragmentDetalleInquilinosBinding binding;

    public static DetalleInquilinosFragment newInstance() {
        return new DetalleInquilinosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = new ViewModelProvider(this).get(DetalleInquilinosViewModel.class);
        binding = FragmentDetalleInquilinosBinding.inflate(inflater, container, false);

        mv.getInquilino().observe(getViewLifecycleOwner(), inquilino -> {
            binding.etNombre.setText(inquilino.getNombre());
            binding.etApellido.setText(inquilino.getApellido());
            binding.etDni.setText(inquilino.getDni());
            binding.etTelefono.setText(inquilino.getTelefono());
            binding.etCorreo.setText(inquilino.getEmail());
        });
        mv.obtenerInquilino(getArguments());
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