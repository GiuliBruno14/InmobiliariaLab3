package com.giulietta.inmobiliaria.ui.inmuebles;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder> {
    private List<Inmueble> lista;
    private Context context;
    public InmuebleAdapter(List<Inmueble> lista, Context context){
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public InmuebleAdapter.InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.inmueble_card, parent, false);
        return new InmuebleViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleAdapter.InmuebleViewHolder holder, int position) {
        Inmueble i = lista.get(position);
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
                .placeholder(null)
                .error("null")
                .into(holder.imgInmueble);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", i);
                Navigation.findNavController((Activity)view.getContext(), R.id.nav_host_fragment_content_main).navigate(R.id.detalleInmuebleFragment, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class InmuebleViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDireccion, tvTipo, tvPrecio, tvEstado;
        private ImageView imgInmueble;
        private CardView cardView;
        public InmuebleViewHolder(@NonNull View itemView) {
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
