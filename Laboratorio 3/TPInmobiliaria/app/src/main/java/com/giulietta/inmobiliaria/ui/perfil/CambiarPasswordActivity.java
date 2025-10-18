package com.giulietta.inmobiliaria.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.databinding.ActivityCambiarPasswordBinding;


public class CambiarPasswordActivity extends AppCompatActivity {

    private CambiarPasswordViewModel mv;
    private ActivityCambiarPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mv = new ViewModelProvider(this).get(CambiarPasswordViewModel.class);
        binding = ActivityCambiarPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mv.getmMensaje().observe(this, mensaje ->
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        );
        mv.getCambioExitoso().observe(this,estado ->{
            if(Boolean.TRUE.equals(estado)){
                finish();
            }
        });

        binding.btnCambiarPassword.setOnClickListener(view -> {
            String actual = binding.etPasswordActual.getText().toString().trim();
            String nueva1 = binding.etPasswordNueva.getText().toString().trim();
            String nueva2 = binding.etPasswordConfirmar.getText().toString().trim();
            mv.cambiarPassword(this, actual, nueva1, nueva2);
        });

    }
}