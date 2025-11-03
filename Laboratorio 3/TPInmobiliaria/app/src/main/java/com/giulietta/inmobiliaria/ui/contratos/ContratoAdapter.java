package com.giulietta.inmobiliaria.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.modelo.Inmueble;
import com.giulietta.inmobiliaria.request.ApiClient;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ContratoViewHolder> {

    private List<Inmueble> listaInmueblesConContrato;
    private Context context;

    public ContratoAdapter(List<Inmueble> lista, Context context){
        this.listaInmueblesConContrato = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.inmueble_card, parent, false);
        return new ContratoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoViewHolder holder, int position) {
        Inmueble i = listaInmueblesConContrato.get(position);

        holder.tvDireccion.setText(i.getDireccion());
        holder.tvTipo.setText(i.getTipo());
        holder.tvPrecio.setText(String.valueOf(i.getPrecio()));
        String estado = String.valueOf(i.getDisponible());
        if (estado.equalsIgnoreCase("SI")) {
            holder.tvEstado.setText("Disponible");
        } else {
            holder.tvEstado.setText("No Disponible");
            holder.tvEstado.setTextColor(Color.RED);
        }
        Glide.with(context)
                .load(ApiClient.URLBASE + i.getImagen())
                .placeholder(R.drawable.ic_inmuebles)
                .error(R.drawable.ic_logout)
                .into(holder.imgInmueble);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("idInmueble", i.getId());
                Navigation.findNavController((Activity)view.getContext(), R.id.nav_host_fragment_content_main).navigate(R.id.detalleContratoFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaInmueblesConContrato.size();
    }
    
    public class ContratoViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDireccion, tvTipo, tvPrecio, tvEstado;
        private ImageView imgInmueble;
        private CardView cardView;

        public ContratoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            imgInmueble = itemView.findViewById(R.id.ivFotoInmueble);
            cardView = itemView.findViewById(R.id.idCard);
        }
    }
}
