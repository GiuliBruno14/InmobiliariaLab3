package com.giulietta.inmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.giulietta.inmobiliaria.databinding.FragmentPagosBinding;
import com.giulietta.inmobiliaria.modelo.Inmueble;
import com.giulietta.inmobiliaria.modelo.Pago;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel mv;
    private FragmentPagosBinding binding;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = new ViewModelProvider(this).get(PagosViewModel.class);
        binding = FragmentPagosBinding.inflate(inflater, container, false);
        mv.getPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                android.util.Log.d("PagosFragment", "Mostrando " + pagos.size() + " pagos");
                PagosAdapter adapter = new PagosAdapter(pagos, getContext());
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                RecyclerView rv = binding.rvPagos;
                rv.setLayoutManager(llm);
                rv.setAdapter(adapter);
            }
        });
        if (getArguments() != null && getArguments().containsKey("IdContrato")) {
            int idContrato = getArguments().getInt("IdContrato");
            android.util.Log.d("PagosFragment", "ID Contrato recibido: " + idContrato);
            mv.leerPagos(idContrato);
        } else {
            Toast.makeText(getContext(), "No se encontró el ID del contrato.", Toast.LENGTH_SHORT).show();
        }
        mv.getMensaje().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}