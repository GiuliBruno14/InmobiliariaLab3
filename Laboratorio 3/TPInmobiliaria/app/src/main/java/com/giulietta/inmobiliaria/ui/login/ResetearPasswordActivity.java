package com.giulietta.inmobiliaria.ui.login;

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
import com.giulietta.inmobiliaria.databinding.ActivityResetearPasswordBinding;

public class ResetearPasswordActivity extends AppCompatActivity {
    private ActivityResetearPasswordBinding binding;
    private ResetearPasswordViewModel mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetearPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mv = new ViewModelProvider(this).get(ResetearPasswordViewModel.class);

        mv.getmMensaje().observe(this, mensaje -> {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            if (mensaje.contains("Correo enviado")){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnEnviar.setOnClickListener(view ->
        {
            String email = binding.etEmail.getText().toString().trim();
            mv.enviarCorreoRecuperacion(email);
        });

    }


}