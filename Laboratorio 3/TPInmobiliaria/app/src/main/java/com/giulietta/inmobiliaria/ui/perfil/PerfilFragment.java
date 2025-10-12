package com.giulietta.inmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.giulietta.inmobiliaria.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel mv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mv = new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        mv.getPropietarioLiveData().observe(getViewLifecycleOwner(), propietario -> {
            if(propietario != null){
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etDni.setText(propietario.getDni());
                binding.etEmail.setText(propietario.getEmail());
                binding.etTelefono.setText(propietario.getTelefono());
                binding.etDomicilio.setText(propietario.getDomicilio());
                binding.etCiudad.setText(propietario.getCiudad());
            }
        });

        mv.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if(error !=null){
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
        mv.cargarPerfil(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}