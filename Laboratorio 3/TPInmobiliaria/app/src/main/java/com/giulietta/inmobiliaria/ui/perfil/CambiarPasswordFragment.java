package com.giulietta.inmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.giulietta.inmobiliaria.databinding.FragmentCambiarPasswordBinding;

public class CambiarPasswordFragment extends Fragment {

    private CambiarPasswordViewModel mv;
    private FragmentCambiarPasswordBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCambiarPasswordBinding.inflate(inflater, container, false);
        mv = new ViewModelProvider(this).get(CambiarPasswordViewModel.class);

        // Observadores del ViewModel
        mv.getmMensaje().observe(getViewLifecycleOwner(), mensaje ->
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show()
        );

        //mv.getCambioExitoso().observe(getViewLifecycleOwner(), estado -> {
            //if (Boolean.TRUE.equals(estado)) {
            //    requireActivity().onBackPressed(); // vuelve atrás al finalizar
          //  }
        //});

        // Botón Cambiar Contraseña
        binding.btnCambiarPassword.setOnClickListener(view -> {
            String actual = binding.etPasswordActual.getText().toString().trim();
            String nueva1 = binding.etPasswordNueva.getText().toString().trim();
            String nueva2 = binding.etPasswordConfirmar.getText().toString().trim();
            mv.cambiarPassword(getContext(), actual, nueva1, nueva2);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

