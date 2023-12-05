package com.example.examen_aguedapl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goto_Cronometro(View v) { //boton BtnCronometro, lleva a la siguiente actividad
        Intent i = new Intent(this, Cronometro.class);
        startActivity(i);
    }

    public void cerrarApp(View v) { //boton Salir, salta un dialog que confirma si se desea salir de la app
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setMessage("¿Cerrar la aplicación?")
                .setTitle("Salir")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialogo = constructor.create();
        dialogo.show();
    }
}