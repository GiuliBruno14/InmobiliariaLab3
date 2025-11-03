package com.giulietta.inmobiliaria.ui.inquilinos;

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

import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.databinding.FragmentContratosBinding;
import com.giulietta.inmobiliaria.modelo.Inmueble;
import com.giulietta.inmobiliaria.ui.contratos.ContratoAdapter;
import com.giulietta.inmobiliaria.ui.contratos.ContratosViewModel;

import java.util.List;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel vm;
    private FragmentContratosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InquilinosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        vm.getInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InquilinosAdapter adapter = new InquilinosAdapter(inmuebles, getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
                RecyclerView rv = binding.rvInmueblesContrato;
                rv.setLayoutManager(glm);
                rv.setAdapter(adapter);
            }
        });
        return binding.getRoot();
    }

}