package com.giulietta.inmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.databinding.FragmentContratosBinding;
import com.giulietta.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.giulietta.inmobiliaria.modelo.Inmueble;
import com.giulietta.inmobiliaria.ui.inmuebles.InmuebleAdapter;
import com.giulietta.inmobiliaria.ui.inmuebles.InmueblesViewModel;

import java.util.List;

public class ContratosFragment extends Fragment {

    private ContratosViewModel mv;
    private FragmentContratosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        mv.getInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                ContratoAdapter adapter = new ContratoAdapter(inmuebles, getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
                RecyclerView rv = binding.rvInmueblesContrato;
                rv.setLayoutManager(glm);
                rv.setAdapter(adapter);
            }
        });
        mv.getMensaje().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}