package com.example.trabajofinal3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlantaAdapter extends RecyclerView.Adapter<PlantaAdapter.PlantaViewHolder> {

    private ArrayList<Planta> listaPlantas;
    private Context context;

    public PlantaAdapter(ArrayList<Planta> listaPlantas, Context context) {
        this.listaPlantas = listaPlantas;
        this.context = context; // Pasamos el contexto para poder referenciarlo en el toast al borrar la planta
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
        long diasRestantes = planta.getDiasParaRiego()+1; //El +1 hace que coja bien los dias.
        if(diasRestantes == 0) {
            holder.tiempoRiego.setText("Regar hoy.");
        }
        else if(diasRestantes == 1){
            holder.tiempoRiego.setText("Regar mañana.");
        }else{
            holder.tiempoRiego.setText("Regar en " + diasRestantes + " días.");
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
                Toast.makeText(context, context.getString(R.string.planta_borrada), Toast.LENGTH_SHORT).show();
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
