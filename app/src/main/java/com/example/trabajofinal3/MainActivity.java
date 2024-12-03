package com.example.trabajofinal3;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

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

        // Configuración del TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        // Añadir pestañas
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Acción al seleccionar una pestaña
                if (tab.getPosition() == 1) {
                    Toast.makeText(getApplicationContext(), "PROXIMAMENTE", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Tab seleccionada: " + tab.getText(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Acción al deseleccionar una pestaña
                // Comentado para que no se repita al seleccioanr otra y salgan 2 toast
                //Toast.makeText(getApplicationContext(), "Tab deseleccionada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Acción al volver a seleccionar una pestaña
                Toast.makeText(getApplicationContext(), "Tab reseleccionada", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        listaPlantas = new ArrayList<>();
        //Ejemplo para empezar con una planta la App
        listaPlantas.add(new Planta("Rosa",10));

        // Instanciar y setear un adaptador para integrar la vista del item como base de la lista del recyclerView
        plantaAdapter = new PlantaAdapter(listaPlantas);
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
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), getString(R.string.notificaciones_activadas), Snackbar.LENGTH_LONG);
                    snackbar.setAction("Deshacer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Acción al hacer clic en "Deshacer"
                            Toast.makeText(getApplicationContext(), "Acción deshecha", Toast.LENGTH_SHORT).show();
                        }
                    });
                    snackbar.addCallback(new Snackbar.Callback() {
                        @Override
                        public void onShown(Snackbar sb) {
                            // Acción al mostrar la Snackbar
                            System.out.println("Snackbar mostrada");
                        }

                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            // Acción al cerrar la Snackbar
                            System.out.println("Snackbar cerrada");
                        }
                    });
                    snackbar.show();
                } else {
                    // Switch desactivado
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), getString(R.string.notificaciones_desactivadas), Snackbar.LENGTH_LONG);
                    snackbar.setAction("Deshacer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Acción al hacer clic en "Deshacer"
                            Toast.makeText(getApplicationContext(), "Acción deshecha", Toast.LENGTH_SHORT).show();
                        }
                    });
                    snackbar.addCallback(new Snackbar.Callback() {
                        @Override
                        public void onShown(Snackbar sb) {
                            // Acción al mostrar la Snackbar
                            System.out.println("Snackbar mostrada");
                        }

                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            // Acción al cerrar la Snackbar
                            System.out.println("Snackbar cerrada");

                        }
                    });
                    snackbar.show();
                }
            }
        });

        TextView plantas = findViewById(R.id.textView2);
        plantas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpMenu(v);
            }
        });

    }

    // Método para asociar un menú emergente popup al pulsar el textView
    public void showPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu, popupMenu.getMenu());
        // Manejador de clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                TextView tv;
                if (item.getItemId() == R.id.itemAmarillo) {
                    tv = (TextView) view;
                    tv.setTextColor(Color.YELLOW);
                }
                if (item.getItemId() == R.id.itemVioleta) {
                    tv = (TextView) view;
                    tv.setTextColor(Color.MAGENTA);
                }
                return true;
            }
        });
        // mostrarlo
        popupMenu.show();
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
                } else if (tiempoRiegoStr.matches(".*\\d.*")){
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