package com.giulietta.inmobiliaria.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.giulietta.inmobiliaria.MainActivity;
import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements SensorEventListener {

    private ActivityLoginBinding binding;
    private LoginActivityViewModel mv;
    private SensorManager sensorManager;
    private float acelVal;      // Valor actual de aceleración
    private float acelLast;     // Último valor
    private float shake;        // Valor de sacudida
    private static final int REQUEST_CALL_PERMISSION = 100;
    private static final String PHONE_NUMBER = "2664707207";


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

        binding.tvResetear.setOnClickListener(view -> {
            Intent intent = new Intent(this, ResetearPasswordActivity.class);
            startActivity(intent);
        });

        //SENSORES
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        acelLast = acelVal;
        acelVal = (float) Math.sqrt((x * x + y * y + z * z));
        float delta = acelVal - acelLast;
        shake = shake * 0.9f + delta;

        if (shake > 12) { // sensibilidad
            realizarLlamada();
        }
    }
    private void realizarLlamada() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + PHONE_NUMBER));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PERMISSION);
            return;
        }
        Toast.makeText(this, "Llamando a inmobiliaria...", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                realizarLlamada();
            } else {
                Toast.makeText(this, "Permiso de llamada denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}




