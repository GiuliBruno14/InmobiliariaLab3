package com.giulietta.inmobiliaria.ui.contratos;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.giulietta.inmobiliaria.R;
import com.giulietta.inmobiliaria.modelo.Pago;

import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.PagosViewHolder> {
    private List<Pago> lista;
    private Context context;

    public PagosAdapter(List<Pago> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public PagosAdapter.PagosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.pago_card, parent, false);
        return new PagosAdapter.PagosViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PagosAdapter.PagosViewHolder holder, int position) {
        Pago p = lista.get(position);
        String fecha = DateFormat.format("dd/MM/yyyy", p.getFecha()).toString();

        holder.tvId.setText("Código de pago: " + p.getId());
        holder.tvFecha.setText("Fecha: " + fecha);
        holder.tvReferencia.setText("Referencia: " + p.getReferencia());
        holder.tvImporte.setText("Importe: $" + p.getImporte());
        holder.tvContrato.setText("Contrato ID: " + p.getIdContrato());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class PagosViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvFecha, tvReferencia, tvImporte, tvContrato;

        public PagosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvReferencia = itemView.findViewById(R.id.tvReferencia);
            tvImporte = itemView.findViewById(R.id.tvImporte);
            tvContrato = itemView.findViewById(R.id.tvContrato);

        }
    }
}
