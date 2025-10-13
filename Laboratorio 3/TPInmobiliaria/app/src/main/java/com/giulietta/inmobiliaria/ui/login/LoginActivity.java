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

import com.giulietta.inmobiliaria.MainActivity;
import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginActivityViewModel mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mv = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        // Observador de mensajes de error o info
        mv.getMensaje().observe(this, mensaje -> {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        });

        // Observador de login exitoso
        mv.getLoginExitoso().observe(this, exitoso -> {
            if (exitoso != null && exitoso) {
                // Abrir MainActivity si login fue exitoso
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish(); // Para que no vuelva al login
            }
        });

        // Click del botón Iniciar Sesión
        binding.btnLogin.setOnClickListener(view -> {
            String email = binding.etEmail.getText().toString().trim();
            String clave = binding.etContrasenia.getText().toString().trim();

            // Llamada al ViewModel
            mv.llamarLogin(email, clave);
        });
    }
}