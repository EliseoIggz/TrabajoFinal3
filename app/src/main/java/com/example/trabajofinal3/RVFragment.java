package com.example.trabajofinal3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

public class RVFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlantaAdapter plantaAdapter;
    private ArrayList<Planta> listaPlantas;

    public RVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaPlantas = new ArrayList<>();
        listaPlantas.add(new Planta("Rosa", 10)); // Ejemplo inicial
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View mainView = inflater.inflate(R.layout.fragment_r_v, container, false);

        // Configurar RecyclerView
        recyclerView = mainView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        listaPlantas = new ArrayList<>();
        // Ejemplo de datos iniciales
        listaPlantas.add(new Planta("Rosa", 10));
        plantaAdapter = new PlantaAdapter(listaPlantas, getContext());
        recyclerView.setAdapter(plantaAdapter);

        return mainView;
    }

    // Método para agregar una nueva planta
    public void agregarPlanta(String nombre, int tiempoRiego) {
        Planta nuevaPlanta = new Planta(nombre, tiempoRiego);
        listaPlantas.add(nuevaPlanta);
        ordenarPlantasPorTiempoRiego();
        plantaAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
    }

    // Ordenar las plantas por tiempo de riego
    private void ordenarPlantasPorTiempoRiego() {
        Collections.sort(listaPlantas, (p1, p2) -> Integer.compare(p1.getTiempoRiego(), p2.getTiempoRiego()));
    }
}