package com.example.trabajofinal3;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private RVFragment rvFragment;
    private Button addButton;
    private extraFragment extraFragment;

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

        // Inicializar los Fragment
        rvFragment = new RVFragment();
        extraFragment = new extraFragment();

        // Localizamos el menú de navegación
        BottomNavigationView bnv = findViewById(R.id.menuNav);

        // Configuramos el listener que responda al boton del menu que sea pulsado para que enseñe un fragment u otro
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()== R.id.opc1){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, RVFragment.class, null)
                            .commit();
                    return true;
                }else if(item.getItemId()==R.id.opc2){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, extraFragment.class, null)
                            .commit();
                    return true;
                }else {
                    return false;
                }
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, rvFragment) // Asegúrate de tener un contenedor con este ID en tu layout
                .commit();

        // Configurar botón para agregar plantas
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> mostrarDialogoAgregarPlanta());

        // Configurar switch
        Switch switchNotificaciones = findViewById(R.id.switchNotif);
        switchNotificaciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, getString(R.string.notificaciones_activadas), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.notificaciones_desactivadas), Toast.LENGTH_SHORT).show();
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
                    agregarPlantaAlFragment(nombre, tiempoRiego);
                    // Cerrar el cuadro de diálogo
                    dialog.dismiss();
                }
            }
        });
        // Mostrar el cuadro de diálogo
        dialog.show();
    }

    private void agregarPlantaAlFragment(String nombre, int tiempoRiego) {
        if (rvFragment != null) {
            rvFragment.agregarPlanta(nombre, tiempoRiego);
        }
    }
}