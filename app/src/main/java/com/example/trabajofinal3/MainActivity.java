package com.example.trabajofinal3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlantaAdapter plantaAdapter;
    private ArrayList<Planta> listaPlantas;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        listaPlantas = new ArrayList<>();
        //Ejemplo para empezar con una planta la App
        listaPlantas.add(new Planta("Rosa",10));

        // Instanciar y setear un adaptador para integrar la vista del item como base de la lista del recyclerView
        plantaAdapter = new PlantaAdapter(listaPlantas, this); // Pasamos el contexto actual que usaremos en el toast de borrar planta
        recyclerView.setAdapter(plantaAdapter);
        // Localizamos el boton de añadir plantas
        addButton = findViewById(R.id.addButton);

        // Configurar el evento de clic del botón agregar
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarPlanta();
            }
        });

        // Localizar el Switch
        Switch switchNotificaciones = findViewById(R.id.switchNotif);

        // Configurar el evento OnCheckedChangeListener
        switchNotificaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Switch activado
                    Toast.makeText(MainActivity.this, getString(R.string.notificaciones_activadas), Toast.LENGTH_SHORT).show();
                } else {
                    // Switch desactivado
                    Toast.makeText(MainActivity.this, getString(R.string.notificaciones_desactivadas), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mostrarDialogoAgregarPlanta() {
        // Inflar el layout del diálogo
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_add_planta, null);

        // Crear el cuadro de diálogo
        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).setCancelable(true).create();

        // Referenciar los campos y el botón del diálogo
        EditText etNombre = dialogView.findViewById(R.id.et_nombre);
        EditText etTiempoRiego = dialogView.findViewById(R.id.et_tiempo_riego);
        Button btnConfirmar = dialogView.findViewById(R.id.btn_confirmar);

        // Configurar el evento del botón confirmar
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String tiempoRiegoStr = etTiempoRiego.getText().toString();

                if (nombre.isEmpty() || tiempoRiegoStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR","El nombre y/o el tiempo de riego estan vacíos");
                } else if (!tiempoRiegoStr.matches("^\\d+$")){ // Logica que comprueba que la cadena solo contenga numeros del inicio al final ( por si acaso, el inputType del textfield esta seteado en number y eso solo permite que se introduzcan numeros)
                    Toast.makeText(MainActivity.this, "El tiempo solo puede contener números", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR","El tiempo de riego contiene símbolos o letras");
                }else {
                    int tiempoRiego = Integer.parseInt(tiempoRiegoStr);

                    // Crear y agregar la nueva planta
                    Planta nuevaPlanta = new Planta(nombre, tiempoRiego);
                    listaPlantas.add(nuevaPlanta);

                    // Ordenar el ArrayList según el tiempo de riego
                    ordenarPlantasPorTiempoRiego();

                    // Notificar al adaptador después de actualizar la lista
                    plantaAdapter.notifyDataSetChanged();

                    // Scroll al inicio si es necesario
                    recyclerView.scrollToPosition(0);

                    // Cerrar el cuadro de diálogo
                    dialog.dismiss();
                }
            }
        });
        // Mostrar el cuadro de diálogo
        dialog.show();
    }

    // Ordena los elementos de la lista por el tiempo de riego
    private void ordenarPlantasPorTiempoRiego() {
        Collections.sort(listaPlantas, new java.util.Comparator<Planta>() {
            @Override
            public int compare(Planta p1, Planta p2) {
                return Integer.compare(p1.getTiempoRiego(), p2.getTiempoRiego());
            }
        });
    }
}