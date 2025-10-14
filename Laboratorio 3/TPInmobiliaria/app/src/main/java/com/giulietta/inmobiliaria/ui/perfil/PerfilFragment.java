package com.giulietta.inmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
            }
        });
        mv.getEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etNombre.setEnabled(aBoolean);
                binding.etApellido.setEnabled(aBoolean);
                binding.etDni.setEnabled(aBoolean);
                binding.etEmail.setEnabled(aBoolean);
                binding.etTelefono.setEnabled(aBoolean);
            }
        });

        mv.getNombreBt().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditarPerfil.setText(s);
            }
        });

        mv.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if(error !=null){
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
        mv.cargarPerfil(requireContext());
        binding.btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String email = binding.etEmail.getText().toString();
                String dni = binding.etDni.getText().toString();
                String telefono = binding.etTelefono.getText().toString();
                mv.guardar(binding.btnEditarPerfil.getText().toString(),nombre, apellido, email,dni, telefono);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}