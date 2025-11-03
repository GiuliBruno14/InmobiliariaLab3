package com.giulietta.inmobiliaria.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.databinding.FragmentCargarInmuebleBinding;
import com.giulietta.inmobiliaria.modelo.TipoInmuebles;

import java.util.List;

public class CargarInmuebleFragment extends Fragment {

    private CargarInmuebleViewModel mViewModel;

    public static CargarInmuebleFragment newInstance() {
        return new CargarInmuebleFragment();
    }
    private CargarInmuebleViewModel vm;
    private FragmentCargarInmuebleBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(CargarInmuebleViewModel.class);
        binding = FragmentCargarInmuebleBinding.inflate(inflater, container, false);
        abrirGaleria();
        vm.obtenerTipos();
        vm.getTipos().observe(getViewLifecycleOwner(), tipoInmuebles -> {
            //Ver que hay en tipos
            for (TipoInmuebles tipo : tipoInmuebles) {
                System.out.println(tipo.getTipo());
            }
            //Enttra aca??
            System.out.println("ENTRA ACA?");//
            ArrayAdapter<TipoInmuebles> adapter = new ArrayAdapter<>(
                    requireContext(),
                    R.layout.dropdown_menu_item, // layout simple de TextView
                    tipoInmuebles
            );
            binding.spTipo.setAdapter(adapter);
        });
        binding.spTipo.setOnClickListener(v -> binding.spTipo.showDropDown());

        binding.btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        vm.getUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivPreviewImagen.setVisibility(View.VISIBLE);

                binding.ivPreviewImagen.setImageURI(uri);
                Glide.with(requireContext())
                        .load(uri)
                        .centerCrop() // o .fitCenter() si preferís que se vea completa
                        .into(binding.ivPreviewImagen);
            }
        });
        binding.btnGuardarInmueble.setOnClickListener( v -> {
            String direccion = binding.etDireccion.getText().toString();
            String uso = binding.etUso.getText().toString();
            String tipo = binding.spTipo.getText().toString();
            int ambientes = Integer.parseInt(binding.etAmbientes.getText().toString());
            int latitud = Integer.parseInt(binding.etLatitud.getText().toString());
            int longitud = Integer.parseInt(binding.etLongitud.getText().toString());
            double precio = Double.parseDouble(binding.etPrecio.getText().toString());
            vm.agregarInmueble(direccion, uso, tipo, ambientes, latitud, longitud, precio);
        });
        vm.getText().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                vm.recibirFoto(result);

            }
        });
    }

}