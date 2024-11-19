package com.example.trabajofinal3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlantaAdapter extends RecyclerView.Adapter<PlantaAdapter.PlantaViewHolder> {

    private ArrayList<Planta> listaPlantas;

    public PlantaAdapter(ArrayList<Planta> listaPlantas) {
        this.listaPlantas = listaPlantas;
    }

    @NonNull
    @Override
    public PlantaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new PlantaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantaViewHolder holder, int position) {
        Planta planta = listaPlantas.get(position);
        holder.nombre.setText(planta.getNombre());
        // Mostrar los días restantes para el riego
        long diasRestantes = planta.getDiasParaRiego();
        if(diasRestantes == 0) {
            holder.tiempoRiego.setText("Regar hoy");
        }else{
            holder.tiempoRiego.setText("Días para riego: " + diasRestantes);
        }
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    // Eliminar el elemento de la lista
                    listaPlantas.remove(pos);
                    // Notificar al adaptador
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, listaPlantas.size());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPlantas.size();
    }

    public class PlantaViewHolder extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView nombre, tiempoRiego;
        ImageButton deleteButton;

        public PlantaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.planta_nombre);
            tiempoRiego = itemView.findViewById(R.id.planta_tiempoRiego);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

}
